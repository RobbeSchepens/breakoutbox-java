package gui;

import domein.DomeinController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class OefeningDetailPanelController extends VBox {

    @FXML private Label lblTitleRight;
    @FXML private Label lblNaam;
    @FXML private TextField txfNaam;
    @FXML private Button btnAdd;
    
    private DomeinController dc;

    public OefeningDetailPanelController(DomeinController dcon) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("OefeningDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dcon;
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
    }
    
}
