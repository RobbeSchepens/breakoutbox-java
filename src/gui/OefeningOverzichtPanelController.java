package gui;

import domein.DomeinController;
import domein.IOefening;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
        getTbvOverzichtCol1().setCellValueFactory(new PropertyValueFactory<>("Naam"));
        getTbvOverzichtCol2().setCellValueFactory(new PropertyValueFactory<>("Vak"));
//        getTbvOverzichtCol2().setCellValueFactory(v->v.getValue().getVak().naamProperty());
    }
}
