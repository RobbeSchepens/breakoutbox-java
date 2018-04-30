/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.ISessie;
import domein.OefeningController;
import domein.SessieController;
import java.nio.channels.SeekableByteChannel;
import javafx.event.ActionEvent;

/**
 *
 * @author Daan
 */
public class OverzichtPanelSessieController extends OverzichtPanelController<ISessie, SessieController> {

    private SessieController sc;
    private FrameSessieController fc;

    public OverzichtPanelSessieController(SessieController sc, FrameSessieController fc) {
        super(sc);

        this.sc = sc;
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
        setLblTitleLeftText("Overzicht Sessies");
        setLblFilterOpText("Filter op sessie:");
        renderTable();
    }

    @Override
    void filter(String newValue) {
        System.out.println("filter");
    }

    @Override
    void initNieuw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void renderTable() {
        System.out.println("render table hier");
    }

}
