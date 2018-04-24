package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class FrameOefeningController extends GridPane {
    public FrameOefeningController(DomeinController dc) {
        OverzichtPanelController overzichtPanelController 
                = new OverzichtPanelOefeningController(dc);
        DetailPanelOefeningController oefeningDetailPanelController 
                = new DetailPanelOefeningController(dc);
        
        dc.addObserver(overzichtPanelController);
        dc.addOefeningObserver(oefeningDetailPanelController);
        add(overzichtPanelController, 0, 0);
        add(oefeningDetailPanelController, 1, 0);
    }
}