package gui;

import domein.DomeinController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public abstract class OverzichtPanelController <T> {

    @FXML private Label lblTitleLeft, lblFilterOp;
    @FXML private TextField txfFilterOp;
    @FXML private TableView<T> tbvOverzicht;
    @FXML private TableColumn<T, String> tbvOverzichtCol1;
    @FXML private TableColumn<T, String> tbvOverzichtCol2;
    @FXML private Button btnDeleteSelected; 
    
    private DomeinController dc;

    public OverzichtPanelController(DomeinController dcon) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("OverzichtPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dcon;
        
        tbvOverzicht.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                implementTableviewListener(newValue);
            }
        });
        
        renderContent();
    }

    public void setLblTitleLeftText(String lblTitleLeftText) {
        this.lblTitleLeft.setText(lblTitleLeftText);
    }

    public void setLblFilterOpText(String lblFilterOpText) {
        this.lblFilterOp.setText(lblFilterOpText);
    }

    public TableView<T> getTbvOverzicht() {
        return tbvOverzicht;
    }

    public TableColumn<T, String> getTbvOverzichtCol1() {
        return tbvOverzichtCol1;
    }

    public TableColumn<T, String> getTbvOverzichtCol2() {
        return tbvOverzichtCol2;
    }
    
    
    
    

    @FXML private void btnDeleteSelectedOnAction(ActionEvent event){
        if (tbvOverzicht.getSelectionModel().getSelectedItem() != null)
            dc.verwijderObject(tbvOverzicht.getSelectionModel().getSelectedItem());
    }
    //@FXML abstract void btnDeleteSelectedOnAction(ActionEvent event);
    abstract void implementTableviewListener(T newValue);
    abstract void renderContent();
}