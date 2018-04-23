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
        getChildren().addAll(overzichtPanelController);
    }
}