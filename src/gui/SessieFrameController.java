package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class SessieFrameController extends GridPane {

    NavigatieController nav;
    SessieOverzichtPanelController sessieOverzicht;

    public SessieFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        sessieOverzicht = new SessieOverzichtPanelController(dc.getSc(), this);

        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(sessieOverzicht, 0, 1);
    }
}