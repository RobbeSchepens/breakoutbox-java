package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class OefeningFrameController extends GridPane {
    private DomeinController dc;
    private OverzichtPanelController overzichtPanelController;
    private OefeningDetailPanelController oefeningDetailPanelController;
    
    public OefeningFrameController(DomeinController dc) {
        this.dc = dc;    
        overzichtPanelController = new OefeningOverzichtPanelController(dc);
        oefeningDetailPanelController = new OefeningDetailPanelController(dc);
        
        //dc.addObserver(oefeningDetailPanelController);
        
        getChildren().addAll(overzichtPanelController, oefeningDetailPanelController);
    }
}
