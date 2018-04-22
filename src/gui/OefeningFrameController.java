package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class OefeningFrameController extends GridPane {
    private DomeinController dc;
    private OverzichtPanelController overzichtPanel;
    private OefeningDetailPanelController oefeningDetailPanelController;
    
    public OefeningFrameController(DomeinController dc) {
        this.dc = dc;    
        overzichtPanel = new OefeningOverzichtPanelController(dc);
        oefeningDetailPanelController = new OefeningDetailPanelController(dc);
        
        //dc.addObserver(oefeningDetailPanelController);
        
        //getChildren().addAll(overzichtPanel, oefeningDetailPanelController);
    }
}
