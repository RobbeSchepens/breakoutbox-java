package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public final class HomeFrameController extends GridPane {

    private final NavigatieController nav;
    private final HoofdMenuController hoofdmenu;
    
    public HomeFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        add(hoofdmenu, 0, 1);
    }
}