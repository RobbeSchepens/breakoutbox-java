package gui;

import domein.BoxController;
import domein.BoxObserver;
import domein.BoxSubject;
import domein.IBox;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class OverzichtPanelBoxController extends OverzichtPanelController<IBox, BoxController> implements BoxSubject {

    BoxController bc;
    FrameBoxController fc;
    private Set<BoxObserver> observers;

    public OverzichtPanelBoxController(BoxController bc, FrameBoxController fc) {
        super(bc);
        this.observers = new HashSet<>();
        this.bc = bc;
        this.fc = fc;
        renderContent();
    }

    @Override
    <T> void implementTableviewListener(T newValue) {
        System.out.println("tbvl");
        bc.setHuidigeBox((IBox) newValue);
        notifyObservers();
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht Boxes");
        setLblFilterOpText("Filter op box:");
        renderTable();
    }

    private void renderTable() {
        // Set items for tableview
        getTbvOverzicht().setItems(bc.geefBoxen());

        // Create new columns based on current class
        TableColumn<IBox, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(v -> v.getValue().naamProperty());

        TableColumn<IBox, String> col2 = new TableColumn<>("Aantal acties");
        col2.setCellValueFactory(v -> new ReadOnlyObjectWrapper(v.getValue().getActies().size()));

        TableColumn<IBox, String> col3 = new TableColumn<>("Aantal oefeningen");
        col3.setCellValueFactory(v -> new ReadOnlyObjectWrapper(v.getValue().getOefeningen().size()));

        // Add the columns to the tableview
        getTbvOverzicht().getColumns().setAll(col1, col2, col3);
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
                bc.delete(getTbvOverzicht().getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    public void addBoxObserver(BoxObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeBoxObserver(BoxObserver o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        IBox o = getTbvOverzicht().getSelectionModel().getSelectedItem();
        observers.forEach((observer) -> {
            observer.update(o);
        });
    }
    
    @Override
    void filter(String newValue) {
        bc.veranderFilter(newValue);
    }

    @Override
    void clearAddedFilters() {}

    @Override
    void initNieuw() {
        fc.initNieuweBox();
    }

}