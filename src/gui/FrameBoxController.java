package gui;

import domein.BoxController;
import domein.DomeinControllerOefening;
import domein.KlasController;
import javafx.scene.layout.GridPane;

public class FrameBoxController extends GridPane {

    MenubarController nav;
    OverzichtPanelBoxController overzichtPanelController;
    DetailPanelBoxController boxesDetailPanelController;
    
    public FrameBoxController(DomeinControllerOefening dc, KlasController kc, BoxController bc) {
        nav = new MenubarController(dc, kc, bc);
        overzichtPanelController = new OverzichtPanelBoxController(bc, this);
        boxesDetailPanelController = new DetailPanelBoxController(bc, this);
        //hoofdmenu = new HoofdMenuController(dc);
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(overzichtPanelController, 0, 1);
        add(boxesDetailPanelController, 1, 1);

        //add(hoofdmenu, 0, 1);
    }
}