package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class FrameOefeningController extends GridPane {
    
    OverzichtPanelOefeningController overzichtPanelController;
    DetailPanelOefeningController oefeningDetailPanelController;
    
    public FrameOefeningController(DomeinController dc) {
        overzichtPanelController = new OverzichtPanelOefeningController(dc);
        oefeningDetailPanelController = new DetailPanelOefeningController(dc);
        
        dc.addObserver(overzichtPanelController);
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelController);
        
        add(overzichtPanelController, 0, 0);
        add(oefeningDetailPanelController, 1, 0);
    }
}