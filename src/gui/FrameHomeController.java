package gui;

import domein.ActieController;
import domein.BoxController;
import domein.OefeningController;
import domein.KlasController;
import domein.SessieController;
import javafx.scene.layout.GridPane;

public class FrameHomeController extends GridPane {

    MenubarController nav;
    HoofdMenuController hoofdmenu;
    
    public FrameHomeController(OefeningController dc, KlasController kc, BoxController bc, SessieController sc, ActieController ac) {
        nav = new MenubarController(dc, kc, bc, sc, ac);
        hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        add(hoofdmenu, 0, 1);
    }
}