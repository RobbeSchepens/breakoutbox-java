package gui;

import domein.DomeinController;
import domein.IOefening;
import java.util.Observable;
import javafx.scene.control.TableColumn;

public class OefeningOverzichtPanelController<T extends IOefening> extends OverzichtPanelController {

    private DomeinController dc;
    
    public OefeningOverzichtPanelController(DomeinController dcon) {
        super(dcon);
        this.dc = dcon;
        renderContent();
    }
    
    @Override
    void implementTableviewListener(Object newValue) {
        dc.setHuidigeOefening((T)newValue);
        //dc.addOefeningObserver(this); // oorzaak van IndexOutOfBoundsException ...
        //renderContent();
    }

    @Override
    void renderContent() {
        getTbvOverzicht().setItems(dc.geefOefeningen());
        TableColumn<T, String> col1 = (TableColumn<T, String>)getTbvOverzichtCol1();
        TableColumn<IOefening, String> col2 = (TableColumn<IOefening, String>)getTbvOverzichtCol2();
        col1.setCellValueFactory(v->v.getValue().naamProperty());
        col2.setCellValueFactory(v->v.getValue().getVak().naamProperty());
    }

    @Override
    public void update(Observable o, Object arg) {
        renderContent();
    }
}
