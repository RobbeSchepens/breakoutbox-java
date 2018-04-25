package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class FrameOefeningController extends GridPane {
    
    OverzichtPanelOefeningController overzichtPanelController;
    DetailPanelOefeningController oefeningDetailPanelController;
    DetailPanelOefeningListController oefeningDetailPanelListController;
    
    public FrameOefeningController(DomeinController dc) {
        overzichtPanelController = new OverzichtPanelOefeningController(dc);
        oefeningDetailPanelController = new DetailPanelOefeningController(dc, this);
        oefeningDetailPanelListController = new DetailPanelOefeningListController(dc, this);
        
        dc.addObserver(overzichtPanelController);
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelController);
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelListController);
        
        add(overzichtPanelController, 0, 0);
        add(oefeningDetailPanelController, 1, 0);
        add(oefeningDetailPanelListController, 0, 0);
        toonListview(false);
    }
    
    public void toonListview(boolean flag) {
        overzichtPanelController.setVisible(!flag);
        oefeningDetailPanelListController.setVisible(flag);
    }
}