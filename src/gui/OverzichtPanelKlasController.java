package gui;

import domein.KlasController;
import domein.IKlas;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;

public class OverzichtPanelKlasController extends OverzichtPanelController<IKlas, KlasController> {

    KlasController kc;
    FrameKlassenController fc;

    OverzichtPanelKlasController(KlasController kc, FrameKlassenController fc) {
        super(kc);
        this.kc = kc;
        this.fc = fc;
        renderContent();
    }

    @Override
    void btnDeleteSelectedOnAction(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    <T> void implementTableviewListener(T newValue) {
        kc.setHuidigeKlas((IKlas) newValue);
        //notifyObservers();
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
        
        // Add the columns to the tableview
        getTbvOverzicht().getColumns().setAll(col1);
    }

    @Override
    void filter(String newValue) {
        System.out.println("wis filter"); // aangeroepen als textvak changes
    }

    @Override
    void initNieuw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}