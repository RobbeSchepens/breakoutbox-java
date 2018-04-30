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
    
    public FrameBoxController(OefeningController dc, KlasController kc, BoxController bc, SessieController sc, ActieController ac) {
        nav = new MenubarController(dc, kc, bc, sc, ac);
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