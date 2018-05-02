package gui;

import domein.ISessie;
import domein.SessieController;
import javafx.event.ActionEvent;

public class SessieOverzichtPanelController extends OverzichtPanelController<ISessie, SessieController> {

    private SessieController sc;
    private SessieFrameController fc;

    public SessieOverzichtPanelController(SessieController sc, SessieFrameController fc) {
        super(sc);

        this.sc = sc;
        this.fc = fc;
        renderContent();
    }

    @Override
    void btnDeleteSelectedOnAction() {
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

    @Override
    void clearAddedFilters() {}
}