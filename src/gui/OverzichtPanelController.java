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

public abstract class OverzichtPanelController {

    @FXML private Label lblTitleLeft, lblFilterOp;
    @FXML private TextField txfFilterOp;
    @FXML private TableView<?> tbvOverzicht;
    @FXML private TableColumn<?, ?> col1;
    @FXML private TableColumn<?, ?> col2;
    @FXML private Button btnDeleteSelected; 
    
    private DomeinController dc;

    public OverzichtPanelController(DomeinController dcon) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("DetailPanel.fxml"));
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
                implementTableviewListener();
            }
        });
        
        renderContent();
    }

    @FXML abstract void btnDeleteSelectedOnAction(ActionEvent event);
    abstract void implementTableviewListener();
    abstract void renderContent();
    abstract void setTableColumnObjects();
}
