package gui;

import domein.ActieController;
import domein.BoxController;
import domein.OefeningController;
import domein.KlasController;
import domein.SessieController;
import javafx.scene.layout.GridPane;

public final class FrameOefeningController extends GridPane {
    
    OefeningController dcc;
    MenubarController nav;
    OverzichtPanelOefeningController overzichtPanelController;
    DetailPanelOefeningController oefeningDetailPanelController;
    DetailPanelOefeningListController oefeningDetailPanelListController;
    DetailPanelOefeningListDoelstellingController oefeningDetailPanelListDoelstellingController;
    
    public FrameOefeningController(OefeningController dc, KlasController kc, BoxController bc, SessieController sc, ActieController ac) {
        nav = new MenubarController(dc, kc, bc, sc, ac);
        overzichtPanelController = new OverzichtPanelOefeningController(dc, this); // het scherm met de tabelview
        oefeningDetailPanelController = new DetailPanelOefeningController(dc, this);
        oefeningDetailPanelListController = new DetailPanelOefeningListController(dc, this);
        oefeningDetailPanelListDoelstellingController = new DetailPanelOefeningListDoelstellingController(dc, this);
        
        //dc.addObserver(overzichtPanelController);
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelController); // het scherm met de datails van oefeningen
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelListController); // het scherm met lisviews gbw
        overzichtPanelController.addOefeningObserver(oefeningDetailPanelListDoelstellingController); // het scherm met lisviews doels
        oefeningDetailPanelListController.addOefeningObserver(oefeningDetailPanelController);
        
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(overzichtPanelController, 0, 1);
        add(oefeningDetailPanelController, 1, 1);
        add(oefeningDetailPanelListController, 0, 1);
        add(oefeningDetailPanelListDoelstellingController, 0, 1);
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
        overzichtPanelController.clearSelectedItem();
        oefeningDetailPanelListController.nieuweOefening();
        oefeningDetailPanelListDoelstellingController.nieuweOefening();
    }
}