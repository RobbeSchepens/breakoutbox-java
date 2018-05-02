package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public final class BoxFrameController extends GridPane {

    NavigatieController nav;
    BoxOverzichtPanelController boxOverzicht;
    BoxDetailPanelController boxDetail;
    BoxActiesDetailPanelController boxDetailActies;
    BoxOefDetailPanelController boxDetailOef;
    
    public BoxFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        boxOverzicht = new BoxOverzichtPanelController(dc.getBc(), this);
        boxDetail = new BoxDetailPanelController(dc.getBc(), this);
        boxDetailActies = new BoxActiesDetailPanelController(dc.getBc(), this);
        boxDetailOef = new BoxOefDetailPanelController(dc.getBc(), this);
        
        boxOverzicht.addBoxObserver(boxDetail);
        boxOverzicht.addBoxObserver(boxDetailActies);
        boxOverzicht.addBoxObserver(boxDetailOef);
        boxDetailActies.addBoxObserver(boxDetail);
        boxDetailOef.addBoxObserver(boxDetail);
        boxDetail.addUpdatedItemObserver(boxOverzicht);
        
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(boxOverzicht, 0, 1);
        add(boxDetail, 1, 1);
        add(boxDetailActies, 0, 1);
        add(boxDetailOef, 0, 1);
        toonListview("cancel/init");
    }
    
    public void toonListview(String keuze) {
        switch (keuze.toLowerCase()) {
            case "act":
                boxOverzicht.setVisible(false);
                boxDetailOef.setVisible(false);
                boxDetailActies.setVisible(true);
                break;
            case "oef":
                boxOverzicht.setVisible(false);
                boxDetailActies.setVisible(false);
                boxDetailOef.setVisible(true);
                break;
            case "cancel/init":
                boxDetailActies.setVisible(false);
                boxDetailOef.setVisible(false);
                boxOverzicht.setVisible(true);
                break;
        }
    }

    public void initNieuweBox() {
        boxDetail.initNieuweBox();
        boxOverzicht.clearSelectedItem();
        boxDetailActies.nieuweBox();
        boxDetailOef.nieuweBox();
    }
}