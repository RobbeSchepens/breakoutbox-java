/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.BoxController;
import domein.BoxObserver;
import domein.BoxSubject;
import domein.OefeningController;
import domein.IBox;
import domein.IKlas;
import domein.KlasController;
import domein.KlasObserver;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;

/**
 *
 * @author Daan
 */
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
    void btnDeleteSelectedOnAction(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    <T> void implementTableviewListener(T newValue) {
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
    void filter(String newValue) {
        System.out.println("wis filter"); // aangeroepen als textvak changes
    }

    @Override
    void initNieuw() {
        fc.initNieuweOefening();
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
            observer.CountlistActiesVanBoxTemp();
            observer.CountlistOefeningenVanBoxTemp();
        });
    }

    @Override
    void clearAddedFilters() {}

}
