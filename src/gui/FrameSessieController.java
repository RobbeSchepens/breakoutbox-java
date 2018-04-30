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
class FrameSessieController extends GridPane {

    MenubarController nav;
    OverzichtPanelSessieController overzichtPanelController;

    FrameSessieController(OefeningController dc, KlasController kc, BoxController bc, SessieController scc, ActieController ac) {
        nav = new MenubarController(dc, kc, bc, scc, ac);
        overzichtPanelController = new OverzichtPanelSessieController(scc, this);

        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(overzichtPanelController, 0, 1);

    }

}
