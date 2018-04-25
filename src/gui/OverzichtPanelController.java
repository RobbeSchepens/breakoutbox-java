package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class OverzichtPanelController <T> extends VBox implements Observer {

    private DomeinController dc;
    @FXML private Label lblTitleLeft, lblFilterOp;
    @FXML private TextField txfFilterOp;
    @FXML private TableView<T> tbvOverzicht;
    @FXML private Button btnDeleteSelected;
    @FXML private Button btnDeselect;
    @FXML private Button btnClearFilter;

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
    
    @FXML
    private void btnDeselectOnAction(ActionEvent event) {
        tbvOverzicht.getSelectionModel().clearSelection();
    }

    @FXML
    private void txfFilterOpOnKeyReleased(KeyEvent event) {
        String newValue = txfFilterOp.getText();
        filter(newValue);
    }

    @FXML
    private void btnClearFilterOnAction(ActionEvent event) {
        txfFilterOp.setText("");
        txfFilterOp.requestFocus();
        filter("");
    }

    @FXML abstract void btnDeleteSelectedOnAction(ActionEvent event);
    abstract <T> void implementTableviewListener(T newValue);
    abstract void renderContent();
    abstract void filter(String newValue);
}