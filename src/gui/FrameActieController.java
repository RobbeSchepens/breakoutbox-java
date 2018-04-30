/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.ActieController;
import domein.BoxController;
import domein.KlasController;
import domein.OefeningController;
import domein.SessieController;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Daan
 */
class FrameActieController extends GridPane {

    MenubarController nav;
    OverzichtPanelActieController overzichtPanelController;
    DetailPanelActieController actiesDetailPanelController;

    FrameActieController(OefeningController dc, KlasController kc, BoxController bc, SessieController scc, ActieController ac) {
        nav = new MenubarController(dc, kc, bc, scc, ac);
        overzichtPanelController = new OverzichtPanelActieController(ac, this);
        actiesDetailPanelController = new DetailPanelActieController(ac, this);

        overzichtPanelController.addActieObserver(actiesDetailPanelController);

        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(overzichtPanelController, 0, 1);
        add(actiesDetailPanelController, 1, 1);

    }

    void initNieuweOefening() {
        overzichtPanelController.clearSelectedItem();
        actiesDetailPanelController.initNieuweOefening();
    }

}
