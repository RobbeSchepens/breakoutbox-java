package gui;

import domein.DomeinControllerOefening;
import domein.IOefening;
import domein.OefeningObserver;
import domein.OefeningSubject;
import domein.Vak;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public final class OverzichtPanelOefeningController extends OverzichtPanelController<IOefening, DomeinControllerOefening> implements OefeningSubject {

    private DomeinControllerOefening dc;
    private FrameOefeningController fc;
    private Set<OefeningObserver> observers;
    private ChoiceBox<Vak> ddlVakken;
    
    public OverzichtPanelOefeningController(DomeinControllerOefening dcon, FrameOefeningController fc) {
        super(dcon);
        this.observers = new HashSet<>();
        this.dc = dcon;
        this.fc = fc;
        
        // Filter Vakken toevoegen
        ddlVakken = new ChoiceBox<>();

        super.getHbxFilter().getChildren().add(ddlVakken);
        
//        ddlVakken.getSelectionModel().selectedItemProperty().addListener(
//                (ObservableValue<? extends Vak> observable, Vak oldValue, Vak newValue) -> {
//            if (!(newValue == null)) {
//                dc.veranderFilter(newValue.getNaam());
//            }
//        });
        renderContent();
    }
    
    @Override
    void implementTableviewListener(Object newValue) {
        dc.setHuidigeOefening((IOefening) newValue);
        notifyObservers();
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht oefeningen");
        setLblFilterOpText("Filter op naam:");
        List<Vak> vl = new ArrayList<>();
        vl.add(new Vak("Alle vakken"));
        vl.addAll(dc.geefVakken());
        ddlVakken.setItems(FXCollections.observableArrayList(vl));
        ddlVakken.getSelectionModel().selectFirst();
        renderTable();
    }
    
    private void renderTable() {
        // Set items for tableview
        getTbvOverzicht().setItems(dc.geefOefeningen());
        
        // Create new columns based on current class
        TableColumn<IOefening, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(v -> v.getValue().naamProperty());
        TableColumn<IOefening, String> col2 = new TableColumn<>("Vak");
        col2.setCellValueFactory(v -> v.getValue().getVak().naamProperty());
        
        // Add the columns to the tableview
        getTbvOverzicht().getColumns().setAll(col1, col2);
    }

    @Override
    void btnDeleteSelectedOnAction(ActionEvent event) {
        if (getTbvOverzicht().getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Verwijder oefening");
            alert.setHeaderText("Bent u zeker dat u de oefening wilt verwijderen?");

            // Volgende regel zorgt ervoor dat het icoontje en de stylesheet meegenomen worden
            alert.initOwner(this.getScene().getWindow());
            alert.setGraphic(new ImageView(this.getClass().getResource("/gui/img/favicon.png").toString()));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                dc.delete(getTbvOverzicht().getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    public void addOefeningObserver(OefeningObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeOefeningObserver(OefeningObserver o) {
        observers.remove(o);
    }
    
    private void notifyObservers() {
        IOefening o = getTbvOverzicht().getSelectionModel().getSelectedItem();
        observers.forEach((observer) -> {
            observer.update(o);
        });
    }

    @Override
    void filter(String newValue) {
        dc.veranderFilter(newValue);
    }

    @Override
    void initNieuw() {
        fc.initNieuweOefening();
    }
}
