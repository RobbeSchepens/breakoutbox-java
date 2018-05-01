/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.ActieController;
import domein.ActieObserver;
import domein.ActieSubject;
import domein.BoxObserver;
import domein.IActie;
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

/**
 *
 * @author Daan
 */
public class OverzichtPanelActieController extends OverzichtPanelController<IActie, ActieController> implements ActieSubject {

    ActieController ac;
    FrameActieController fc;
    private Set<ActieObserver> observers;

    public OverzichtPanelActieController(ActieController ac, FrameActieController fc) {
        super(ac);
        this.observers = new HashSet<>();
        this.ac = ac;
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
                ac.delete(getTbvOverzicht().getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    <T> void implementTableviewListener(T newValue) {
        ac.setHuidigeKlas((IActie) newValue);
        notifyObservers();
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht Acties");
        setLblFilterOpText("Filter op actie:");
        renderTable();
    }

    private void renderTable() {
        // Set items for tableview
        getTbvOverzicht().setItems(ac.geefActies());

        // Create new columns based on current class
        TableColumn<IActie, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(v -> v.getValue().naamProperty());

        // Add the columns to the tableview
        getTbvOverzicht().getColumns().setAll(col1);
    }

    @Override
    void filter(String newValue) {
        ac.veranderFilter(newValue);
    }

    @Override
    void initNieuw() {
        fc.initNieuweOefening();
        renderTable();
    }

    @Override
    public void addActieObserver(ActieObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeActieObserver(ActieObserver o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        IActie o = getTbvOverzicht().getSelectionModel().getSelectedItem();
        observers.forEach((observer) -> {
            observer.update(o);
        });
    }

    @Override
    void clearAddedFilters() {}

}
