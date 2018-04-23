package gui;

import domein.DomeinController;
import domein.IOefening;
import javafx.scene.control.TableColumn;

public class OefeningOverzichtPanelController<T extends IOefening> extends OverzichtPanelController {

    private DomeinController dc;
    
    public OefeningOverzichtPanelController(DomeinController dcon) {
        super(dcon);
        this.dc = dcon;
    }
    
    @Override
    void implementTableviewListener(Object newValue) {
        dc.setHuidigeOefening((T)newValue);
        //dc.addOefeningObserver(this); // oorzaak van IndexOutOfBoundsException ...
        renderContent();
    }

    @Override
    void renderContent() {
        getTbvOverzicht().setItems(dc.geefOefeningen());
        TableColumn<T, String> col1 = (TableColumn<T, String>)(getTbvOverzichtCol1());
        TableColumn<T, String> col2 = (TableColumn<T, String>)(getTbvOverzichtCol2());
        col1.setCellValueFactory(v->v.getValue().naamProperty());
        col2.setCellValueFactory(v->v.getValue().getVak().naamProperty());
    }
}
