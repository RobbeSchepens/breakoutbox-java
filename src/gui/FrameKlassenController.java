package gui;

import domein.ActieController;
import domein.BoxController;
import domein.OefeningController;
import domein.KlasController;
import domein.SessieController;
import javafx.scene.layout.GridPane;

public class FrameKlassenController extends GridPane {

    MenubarController nav;
    OverzichtPanelKlasController overzichtPanelController;
    DetailPanelKlasController klassenDetailPanelController;
    FrameKlassenController(OefeningController dc, KlasController kc, BoxController bc, SessieController sc, ActieController ac) {
        nav = new MenubarController(dc, kc, bc, sc, ac);
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

    void notifyChangeVoorAantalLln() {
        overzichtPanelController.notifyChangeVoorAantalLln();
    }
    
    
    
}