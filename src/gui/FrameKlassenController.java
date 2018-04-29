package gui;

import domein.BoxController;
import domein.OefeningController;
import domein.KlasController;
import javafx.scene.layout.GridPane;

public class FrameKlassenController extends GridPane {

    MenubarController nav;
    OverzichtPanelKlasController overzichtPanelController;
    DetailPanelKlasController klassenDetailPanelController;
    FrameKlassenController(OefeningController dc, KlasController kc, BoxController bc) {
        nav = new MenubarController(dc, kc, bc);
        overzichtPanelController = new OverzichtPanelKlasController(kc, this);
        klassenDetailPanelController = new DetailPanelKlasController(kc, this);
        
        overzichtPanelController.addKlasObserver(klassenDetailPanelController);
        
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(overzichtPanelController, 0, 1);
        add(klassenDetailPanelController, 1, 1);


    }
    
    void initNieuweOefening() {
        overzichtPanelController.clearSelectedItem();
        klassenDetailPanelController.initNieuweOefening();


    }
    
    
    
}