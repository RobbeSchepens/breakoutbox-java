package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public final class KlasFrameController extends GridPane {

    private final NavigatieController nav;
    private final KlasOverzichtPanelController klasOverview;
    private final KlasDetailPanelController klasDetail;
    
    public KlasFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        klasOverview = new KlasOverzichtPanelController(dc.getKc(), this);
        klasDetail = new KlasDetailPanelController(dc.getKc(), this);
        
        klasOverview.addKlasObserver(klasDetail);
        
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(klasOverview, 0, 1);
        add(klasDetail, 1, 1);
    }
    
    void initNieuw() {
        klasOverview.clearSelectedItem();
        klasDetail.initNieuweOefening();
    }

    void notifyChangeVoorAantalLln() {
        klasOverview.notifyChangeVoorAantalLln();
    }
}