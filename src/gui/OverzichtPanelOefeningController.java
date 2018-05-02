package gui;

import domein.OefeningController;
import domein.IOefening;
import domein.OefeningObserver;
import domein.OefeningSubject;
import domein.Vak;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public final class OverzichtPanelOefeningController extends OverzichtPanelController<IOefening, OefeningController> implements OefeningSubject {

    private OefeningController dc;
    private FrameOefeningController fc;
    private Set<OefeningObserver> observers;
    private ComboBox<Vak> ddlVakken;
    private String filtervalue = "";
    
    public OverzichtPanelOefeningController(OefeningController dcon, FrameOefeningController fc) {
        super(dcon);
        this.observers = new HashSet<>();
        this.dc = dcon;
        this.fc = fc;
        
        createComboBoxVakken();
        renderContent();
    }
    
    private void createComboBoxVakken() {
        // Filter Vakken toevoegen op voorlaatste positie + marge rechts
        ddlVakken = new ComboBox<>();
        ddlVakken.setPromptText("Alle vakken");
        super.getHbxFilter().getChildren().add(super.getHbxFilter().getChildren().size() - 1, ddlVakken);
        HBox.setMargin(ddlVakken, new Insets(0,15,0,0));
        
        ddlVakken.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Vak> observable, Vak oldValue, Vak newValue) -> {
                    System.out.println("eventlistener vak");
            if (!(newValue == null)) {
                dc.veranderFilter(filtervalue, newValue);
            }
        });
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
        ddlVakken.setItems(dc.geefVakken());
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
        filtervalue = newValue;
        Vak vak = ddlVakken.getSelectionModel().getSelectedItem();
        dc.veranderFilter(newValue, vak);
    }

    @Override
    void clearAddedFilters() {
        ddlVakken.getSelectionModel().clearSelection();
    }

    @Override
    void initNieuw() {
        fc.initNieuweOefening();
    }
}
