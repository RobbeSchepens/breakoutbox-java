package gui;

import domein.DomeinControllerOefening;
import javafx.scene.layout.GridPane;

public class FrameHomeController extends GridPane {

    MenubarController nav;
    HoofdMenuController hoofdmenu;
    
    public FrameHomeController(DomeinControllerOefening dc) {
        nav = new MenubarController(dc);
        hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        add(hoofdmenu, 0, 1);
    }
}