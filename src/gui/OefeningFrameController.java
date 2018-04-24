package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class OefeningFrameController extends GridPane {
    public OefeningFrameController(DomeinController dc) {
        OverzichtPanelController overzichtPanelController 
                = new OefeningOverzichtPanelController(dc);
        OefeningDetailPanelController oefeningDetailPanelController 
                = new OefeningDetailPanelController(dc);
        
        dc.addObserver(overzichtPanelController);
        add(overzichtPanelController, 0, 0);
        add(oefeningDetailPanelController, 1, 0);
    }
}