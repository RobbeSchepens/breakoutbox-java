package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public final class SessieFrameController extends GridPane {

    private final NavigatieController nav;
    private final SessieOverzichtPanelController sessieOverzicht;
    private final SessieDetailPanelController sessieDetail;

    public SessieFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        sessieOverzicht = new SessieOverzichtPanelController(dc.getSc(), this);
        sessieDetail = new SessieDetailPanelController(dc.getSc(), this);

        sessieOverzicht.addSessieObserver(sessieDetail);
        
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(sessieOverzicht, 0, 1);
        add(sessieDetail, 1, 1);
    }
    
    void initNieuw() {
        sessieOverzicht.clearSelectedItem();
        sessieDetail.initNieuw();
    }
}