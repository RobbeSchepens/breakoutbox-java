package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class ActieFrameController extends GridPane {

    NavigatieController nav;
    ActieOverzichtPanelController actieOverzicht;
    ActieDetailPanelController actieDetail;

    ActieFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        actieOverzicht = new ActieOverzichtPanelController(dc.getAc(), this);
        actieDetail = new ActieDetailPanelController(dc.getAc(), this);

        actieOverzicht.addActieObserver(actieDetail);

        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(actieOverzicht, 0, 1);
        add(actieDetail, 1, 1);
    }

    void initNieuweOefening() {
        actieOverzicht.clearSelectedItem();
        actieDetail.initNieuweOefening();
    }
}