package gui;

import domein.DomeinController;
import domein.IOefening;
import javafx.collections.ObservableList;
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
//        getTbvOverzichtCol1().setCellValueFactory(v->v.getValue().naamProperty());
//        colNaam.setCellValueFactory(v->v.getValue().naamProperty());
//        getTbvOverzichtCol2().setCellValueFactory(v->v.getValue().getVak().naamProperty());
    }

    @Override
    void setTableColumnObjects() {
        //super.setTbvOverzicht(new TableView<IOefening>());
//        super.getTbvOverzicht().setItems((ObservableList<Object>)dc.geefOefeningen());
    }
}
