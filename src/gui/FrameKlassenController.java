package gui;

import domein.DomeinControllerOefening;
import javafx.scene.layout.GridPane;

public class FrameKlassenController extends GridPane {
    
    SidebarNavPanelController nav;
    OverzichtPanelKlasController overzichtPanelController;

    FrameKlassenController(DomeinControllerOefening dc) {
        nav = new SidebarNavPanelController(dc);
        overzichtPanelController = new OverzichtPanelKlasController(dc, this);
    }
}