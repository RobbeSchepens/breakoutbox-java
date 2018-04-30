package gui;

import domein.KlasController;
import domein.IKlas;
import domein.KlasObserver;
import domein.KlasSubject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class OverzichtPanelKlasController extends OverzichtPanelController<IKlas, KlasController> implements KlasSubject {

    KlasController kc;
    FrameKlassenController fc;
    private Set<KlasObserver> observers;

    OverzichtPanelKlasController(KlasController kc, FrameKlassenController fc) {
        super(kc);
        this.observers = new HashSet<>();
        this.kc = kc;
        this.fc = fc;
        renderContent();
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
            if (result.get() == ButtonType.OK) {
                kc.delete(getTbvOverzicht().getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    <T> void implementTableviewListener(T newValue) {
        kc.setHuidigeKlas((IKlas) newValue);
        notifyObservers();
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht Klassen");
        setLblFilterOpText("Filter op klas:");
        renderTable();
    }
    
    private void renderTable() {
        // Set items for tableview
        getTbvOverzicht().setItems(kc.geefKlassen());
        
        // Create new columns based on current class
        TableColumn<IKlas, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(v -> v.getValue().naamProperty());

        TableColumn<IKlas, String> col2 = new TableColumn<>("Aantal leerlingen");
        col2.setCellValueFactory(v -> new ReadOnlyObjectWrapper(v.getValue().getLeerlingen().size()));
        
        // Add the columns to the tableview
        getTbvOverzicht().getColumns().setAll(col1, col2);
    }

    @Override
    void filter(String newValue) {
        System.out.println("wis filter"); // aangeroepen als textvak changes
    }

    @Override
    void initNieuw() {
        fc.initNieuweOefening();
        renderTable();
    }

    @Override
    public void addKlasObserver(KlasObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeKlasObserver(KlasObserver o) {
        observers.remove(o);
    }
    public void notifyObservers() {
        IKlas o = getTbvOverzicht().getSelectionModel().getSelectedItem();
        observers.forEach((observer) -> {
            observer.update(o);
        });
    }

    void notifyChangeVoorAantalLln() {
        renderContent();
    }
    
    @Override
    void clearAddedFilters() {}
    
}