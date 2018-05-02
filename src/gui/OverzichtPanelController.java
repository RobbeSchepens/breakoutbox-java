package gui;

import domein.OefeningController;
import java.io.IOException;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.persistence.RollbackException;
import org.eclipse.persistence.exceptions.DatabaseException;

public abstract class OverzichtPanelController<T, E> extends VBox {

    private E controller;

    @FXML private Label lblTitleLeft, lblFilterOp;
    @FXML private TextField txfFilterOp;
    @FXML private TableView<T> tbvOverzicht;
    @FXML private Button btnDeselect;
    @FXML private Button btnClearFilter;
    @FXML private HBox hbxFilter;
    @FXML private Label lblError;
    @FXML private Button btnDelete;

    public OverzichtPanelController(E dcon) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("OverzichtPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.controller = dcon;
        
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

    public HBox getHbxFilter() {
        return hbxFilter;
    }
    
    @FXML
    private void btnDeselectOnAction(ActionEvent event) {
        tbvOverzicht.getSelectionModel().clearSelection();
        initNieuw();
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
        clearAddedFilters();
        filter("");
    }

    public void clearSelectedItem() {
        tbvOverzicht.getSelectionModel().clearSelection();
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        try {
            btnDeleteSelectedOnAction();
        }
        catch (DatabaseException | RollbackException ex){
            lblError.setText("Het object kon niet verwijderd worden want het maakt deel uit van een ander object.");
        }
    }
    
    abstract void btnDeleteSelectedOnAction();
    abstract <T> void implementTableviewListener(T newValue);
    abstract void renderContent();
    abstract void filter(String newValue);
    abstract void initNieuw();
    abstract void clearAddedFilters();
}