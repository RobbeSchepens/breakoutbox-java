package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class HomeFrameController extends GridPane {

    NavigatieController nav;
    HoofdMenuController hoofdmenu;
    
    public HomeFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        add(hoofdmenu, 0, 1);
    }
}