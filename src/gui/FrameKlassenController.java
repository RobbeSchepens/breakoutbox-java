package gui;

import domein.BoxController;
import domein.DomeinControllerOefening;
import domein.KlasController;
import javafx.scene.layout.GridPane;

public class FrameKlassenController extends GridPane {
    
    MenubarController nav;
    OverzichtPanelKlasController overzichtPanelController;

    FrameKlassenController(DomeinControllerOefening dc, KlasController kc, BoxController bc) {
        nav = new MenubarController(dc, kc, bc);
        //hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        //add(hoofdmenu, 0, 1);
    }
}