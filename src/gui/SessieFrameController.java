package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class SessieFrameController extends GridPane {

    NavigatieController nav;
    SessieOverzichtPanelController sessieOverzicht;
    SessieDetailPanelController sessieDetail;

    public SessieFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        sessieOverzicht = new SessieOverzichtPanelController(dc.getSc(), this);
        sessieDetail = new SessieDetailPanelController(dc.getSc(), this);

        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(sessieOverzicht, 0, 1);
        add(sessieDetail, 1, 1);
    }
}