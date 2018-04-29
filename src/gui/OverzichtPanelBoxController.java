/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.BoxController;
import domein.OefeningController;
import domein.IBox;
import domein.KlasController;
import java.util.Observable;
import javafx.event.ActionEvent;

/**
 *
 * @author Daan
 */
public class OverzichtPanelBoxController extends OverzichtPanelController<IBox, BoxController> {

    BoxController bc;
    FrameBoxController fc;

    public OverzichtPanelBoxController(BoxController bc, FrameBoxController fc) {
        super(bc);
        this.bc = bc;
        this.fc = fc;
        renderContent();
    }

    @Override
    void btnDeleteSelectedOnAction(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    <T> void implementTableviewListener(T newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht Boxes");
        setLblFilterOpText("Filter op box:");
    }

    @Override
    void filter(String newValue) {
        System.out.println("wis filter"); // aangeroepen als textvak changes
    }

    @Override
    void initNieuw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
