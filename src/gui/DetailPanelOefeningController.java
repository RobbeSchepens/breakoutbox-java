package gui;

import domein.Doelstelling;
import domein.DomeinController;
import domein.Groepsbewerking;
import domein.OefeningObserver;
import domein.Vak;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DetailPanelOefeningController extends VBox implements OefeningObserver {

    private DomeinController dc;
    @FXML private Label lblTitleRight;
    @FXML private Label lblNaam;
    @FXML private TextField txfNaam;
    @FXML private Button btnAdd;

    public DetailPanelOefeningController(DomeinController dcon) {
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

    @Override
    public void update(String naam, String antwoord, Vak vak, List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
