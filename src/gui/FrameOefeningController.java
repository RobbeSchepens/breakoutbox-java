package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public final class FrameOefeningController extends GridPane {
    
    SidebarNavPanelController nav;
    OverzichtPanelOefeningController overzichtPanelController;
    DetailPanelOefeningController oefeningDetailPanelController;
    DetailPanelOefeningListController oefeningDetailPanelListController;
    DetailPanelOefeningLisDoelstellingController oefeningDetailPanelListDoelstellingController;
    DomeinController dcc;
    
    public FrameOefeningController(DomeinController dc) {
        nav = new SidebarNavPanelController(dc);
        overzichtPanelController = new OverzichtPanelOefeningController(dc, this); // het scherm met de tabelview
        oefeningDetailPanelController = new DetailPanelOefeningController(dc, this);
        oefeningDetailPanelListController = new DetailPanelOefeningListController(dc, this);
        oefeningDetailPanelListDoelstellingController = new DetailPanelOefeningLisDoelstellingController(dc, this);
        
        dc.addObserver(overzichtPanelController);
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelController); // het scherm met de datails van oefeningen
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelListController); // het scherm met lisviews gbw
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelListDoelstellingController); // het scherm met lisviews doels
        
        add(nav, 0, 0);
        add(overzichtPanelController, 1, 0);
        add(oefeningDetailPanelController, 2, 0);
        add(oefeningDetailPanelListController, 1, 0);
        add(oefeningDetailPanelListDoelstellingController, 1, 0);
        toonListview("cancel/init");
    }
    
    public void toonListview(String keuze) {
        switch (keuze.toLowerCase()) {
            case "gbw":
                overzichtPanelController.setVisible(false);
                oefeningDetailPanelListDoelstellingController.setVisible(false);
                oefeningDetailPanelListController.setVisible(true);
                break;
            case "doels":
                overzichtPanelController.setVisible(false);
                oefeningDetailPanelListController.setVisible(false);
                oefeningDetailPanelListDoelstellingController.setVisible(true);
                break;
            case "cancel/init":
                oefeningDetailPanelListController.setVisible(false);
                oefeningDetailPanelListDoelstellingController.setVisible(false);
                overzichtPanelController.setVisible(true);
                break;

        }
    }
    
    public void initNieuweOefening() {
        oefeningDetailPanelController.initNieuweOefening();

        oefeningDetailPanelListController.nieuweOefening();
        oefeningDetailPanelListDoelstellingController.nieuweOefening();

    }
}