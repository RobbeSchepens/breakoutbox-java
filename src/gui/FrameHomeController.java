package gui;

import domein.DomeinControllerOefening;

public class FrameHomeController {

    SidebarNavPanelController nav;
    
    public FrameHomeController(DomeinControllerOefening dc) {
        nav = new SidebarNavPanelController(dc);
    }
}