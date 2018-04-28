/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;

/**
 *
 * @author Daan
 */
public class FrameHomeController {

    SidebarNavPanelController nav;


    public FrameHomeController(DomeinController dc) {
        nav = new SidebarNavPanelController(dc);

    }

}
