package gui;

import domein.ActieController;
import domein.BoxController;
import domein.OefeningController;
import domein.KlasController;
import domein.SessieController;
import javafx.scene.layout.GridPane;

public class FrameBoxController extends GridPane {

    MenubarController nav;
    OverzichtPanelBoxController overzichtPanelController;
    DetailPanelBoxController boxesDetailPanelController;
    DetailPanelBoxListActiesController boxenDetailPanelListActiesController;
    DetailPanelBoxListOefeningenController boxenDetailPanelListOefeningenController;
    
    public FrameBoxController(OefeningController dc, KlasController kc, BoxController bc, SessieController sc, ActieController ac) {
        nav = new MenubarController(dc, kc, bc, sc, ac);
        overzichtPanelController = new OverzichtPanelBoxController(bc, this);
        boxesDetailPanelController = new DetailPanelBoxController(bc, this);
        boxenDetailPanelListActiesController = new DetailPanelBoxListActiesController(bc, this);
        boxenDetailPanelListOefeningenController = new DetailPanelBoxListOefeningenController(bc, this);

        overzichtPanelController.addBoxObserver(boxesDetailPanelController);
        overzichtPanelController.addBoxObserver(boxenDetailPanelListActiesController);
        overzichtPanelController.addBoxObserver(boxenDetailPanelListOefeningenController);
        boxenDetailPanelListActiesController.addBoxObserver(boxesDetailPanelController);
        boxenDetailPanelListOefeningenController.addBoxObserver(boxesDetailPanelController);

        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(overzichtPanelController, 0, 1);
        add(boxesDetailPanelController, 1, 1);
        add(boxenDetailPanelListActiesController, 0, 1);
        add(boxenDetailPanelListOefeningenController, 0, 1);
        toonListview("cancel/init");
        //add(hoofdmenu, 0, 1);
    }
    
    public void toonListview(String keuze) {
        switch (keuze.toLowerCase()) {
            case "act":
                overzichtPanelController.setVisible(false);
                boxenDetailPanelListOefeningenController.setVisible(false);
                boxenDetailPanelListActiesController.setVisible(true);
                break;
            case "oef":
                overzichtPanelController.setVisible(false);
                boxenDetailPanelListActiesController.setVisible(false);
                boxenDetailPanelListOefeningenController.setVisible(true);
                break;
            case "cancel/init":
                boxenDetailPanelListActiesController.setVisible(false);
                boxenDetailPanelListOefeningenController.setVisible(false);
                overzichtPanelController.setVisible(true);
                break;
        }
    }

    public void initNieuweOefening() {
        boxesDetailPanelController.initNieuweOefening();
        overzichtPanelController.clearSelectedItem();
        boxenDetailPanelListActiesController.nieuweOefening();
        boxenDetailPanelListOefeningenController.nieuweOefening();
    }
    
}