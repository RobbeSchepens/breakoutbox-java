package gui;

import domein.BoxController;
import domein.OefeningController;
import domein.KlasController;
import javafx.scene.layout.GridPane;

public class FrameHomeController extends GridPane {

    MenubarController nav;
    HoofdMenuController hoofdmenu;
    
    public FrameHomeController(OefeningController dc, KlasController kc, BoxController bc) {
        nav = new MenubarController(dc, kc, bc);
        hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        add(hoofdmenu, 0, 1);
    }
}