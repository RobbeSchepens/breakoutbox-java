package gui;

import domein.DomeinController;
import domein.IOefening;
import java.io.IOException;
import java.util.Observer;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public abstract class OverzichtPanelController <T> extends VBox implements Observer {

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

    @FXML abstract void btnDeleteSelectedOnAction(ActionEvent event);
    abstract <T> void implementTableviewListener(T newValue);
    abstract void renderContent();
}