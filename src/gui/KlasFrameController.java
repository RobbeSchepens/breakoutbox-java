package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class KlasFrameController extends GridPane {

    NavigatieController nav;
    KlasOverzichtPanelController klasOverview;
    KlasDetailPanelController klasDetail;
    
    KlasFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        klasOverview = new KlasOverzichtPanelController(dc.getKc(), this);
        klasDetail = new KlasDetailPanelController(dc.getKc(), this);
        
        klasOverview.addKlasObserver(klasDetail);
        
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(klasOverview, 0, 1);
        add(klasDetail, 1, 1);
    }
    
    void initNieuweOefening() {
        klasOverview.clearSelectedItem();
        klasDetail.initNieuweOefening();
    }

    void notifyChangeVoorAantalLln() {
        klasOverview.notifyChangeVoorAantalLln();
    }
}