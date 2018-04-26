package gui;

import domein.DomeinController;
import domein.IOefening;
import domein.OefeningObserver;
import domein.OefeningSubject;
import java.util.HashSet;
import java.util.Observable;
import java.util.Optional;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class OverzichtPanelOefeningController extends OverzichtPanelController<IOefening> implements OefeningSubject {

    private DomeinController dc;
    private Set<OefeningObserver> observers;
    
    public OverzichtPanelOefeningController(DomeinController dcon) {
        super(dcon);
        this.observers = new HashSet<>();
        this.dc = dcon;
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
        renderTable();
    }
    
    private void renderTable() {
        // Set items for tableview
        getTbvOverzicht().setItems(dc.geefOefeningenFiltered());
        
        // Create new columns based on current class
        TableColumn<IOefening, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(v -> v.getValue().naamProperty());
        TableColumn<IOefening, String> col2 = new TableColumn<>("Vak");
        col2.setCellValueFactory(v -> v.getValue().getVak().naamProperty());
        
        // Add the columns to the tableview
        getTbvOverzicht().getColumns().setAll(col1, col2);
    }

    @Override
    public void update(Observable o, Object arg) {
        renderTable();
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
                dc.verwijderOefening(getTbvOverzicht().getSelectionModel().getSelectedItem());

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
    
    public void notifyObservers() {
        IOefening o = getTbvOverzicht().getSelectionModel().getSelectedItem();
        observers.forEach((observer) -> {
            observer.update(o);
        });
    }

    @Override
    void filter(String newValue) {
        dc.veranderFilter(newValue);
    }
}
