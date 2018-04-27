/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Daan
 */
public class FrameKlassenController extends GridPane {
    SidebarNavPanelController nav;
    OverzichtPanelKlasController overzichtPanelController;

    FrameKlassenController(DomeinController dc) {
        nav = new SidebarNavPanelController(dc);
        overzichtPanelController = new OverzichtPanelKlasController(dc, this);


    }


}
