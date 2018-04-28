package gui;

import domein.DomeinControllerOefening;
import javafx.scene.layout.GridPane;

public class FrameBoxController extends GridPane {

    MenubarController nav;
    
    public FrameBoxController(DomeinControllerOefening dc) {
        nav = new MenubarController(dc);
        //hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        //add(hoofdmenu, 0, 1);
    }
}