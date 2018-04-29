package gui;

import domein.DomeinControllerOefening;
import domein.KlasController;
import domein.IKlas;
import domein.IOefening;
import java.util.Observable;
import javafx.event.ActionEvent;


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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht Klassen");
        setLblFilterOpText("Filter op klas:");

    }

    @Override
    void filter(String newValue) {
        System.out.println("wis filter"); // aangeroepen als textvak changes
    }

    @Override
    void initNieuw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}