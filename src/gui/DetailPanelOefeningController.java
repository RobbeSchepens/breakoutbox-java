package gui;

import domein.DomeinController;
import domein.IOefening;
import domein.OefeningObserver;
import domein.Vak;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DetailPanelOefeningController extends VBox implements OefeningObserver {

    private DomeinController dc;
    @FXML private Label lblTitleRight;
    @FXML private TextField txfNaam;
    @FXML private Button btnAdd;
    @FXML private TextField txfAntwoord;
    @FXML private ChoiceBox<Vak> ddlVak;
    @FXML private Button btnGroepsbewerkingen;
    @FXML private Label lblGroepsbewerkingenCount;
    @FXML private Button btnDoelstellingen;
    @FXML private Label lblDoelstellingenCount;
    @FXML private Button btnEdit;
    @FXML private Button btnNieuweOefening;
    @FXML private Button btnOpenFeedback;
    @FXML private Button btnFileFeedback;
    @FXML private Label lblFeedback;
    @FXML private Button btnOpenOpgave;
    @FXML private Button btnFileOpgave;
    @FXML private Label lblOpgave;

    public DetailPanelOefeningController(DomeinController dcon) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("DetailPanelOefening.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dcon;
        
        // Init nieuwe oefening
        ddlVak.setItems(dc.geefVakken());
        btnNieuweOefening.setVisible(false);
        btnEdit.setManaged(false);
        btnEdit.setVisible(false);
    }

    @Override
    public void update(IOefening o) {
        btnNieuweOefening.setVisible(true);
        btnAdd.setManaged(false);
        btnAdd.setVisible(false);
        btnEdit.setManaged(true);
        btnEdit.setVisible(true);
        
        txfNaam.setText(o.getNaam());
        txfAntwoord.setText(o.getAntwoord());
        ddlVak.setItems(dc.geefVakken());
        ddlVak.getSelectionModel().select(o.getVak());
        lblOpgave.setText("naam.pdf");
        lblFeedback.setText("naam.pdf");
        lblGroepsbewerkingenCount.setText(o.getGroepsBewerkingen().size() + " bewerkingen geselecteerd");
        lblDoelstellingenCount.setText(o.getDoelstellingen().size() + " doelstellingen geselecteerd");
    }

    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
        btnNieuweOefening.setVisible(false);
        btnAdd.setManaged(true);
        btnAdd.setVisible(true);
        btnEdit.setManaged(false);
        btnEdit.setVisible(false);
        
        txfNaam.setText("");
        txfAntwoord.setText("");
        ddlVak.setItems(dc.geefVakken());
        ddlVak.getSelectionModel().clearSelection();
        lblOpgave.setText("");
        lblFeedback.setText("");
        lblGroepsbewerkingenCount.setText("0 bewerkingen geselecteerd");
        lblDoelstellingenCount.setText("0 doelstellingen geselecteerd");
    }

    @FXML
    private void btnOpenOpgaveOnAction(ActionEvent event) {
    }

    @FXML
    private void btnFileOpgaveOnAction(ActionEvent event) {
    }

    @FXML
    private void btnOpenFeedbackOnAction(ActionEvent event) {
    }

    @FXML
    private void btnFileFeedbackOnAction(ActionEvent event) {
    }

    @FXML
    private void btnGroepsbewerkingenOnAction(ActionEvent event) {
    }

    @FXML
    private void btnDoelstellingenOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        //dc.voegNieuweOefeningToe(txfNaam.getText(), txfAntwoord.getText(), opgave, feedback, vak, groepsbewerkingen, doelstelling);
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
        System.out.println("test");
    }
    
}