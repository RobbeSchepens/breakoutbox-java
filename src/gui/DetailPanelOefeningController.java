package gui;

import domein.Doelstelling;
import domein.DomeinControllerOefening;
import domein.Groepsbewerking;
import domein.IOefening;
import domein.OefeningObserver;
import domein.PDF;
import domein.Vak;
import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DetailPanelOefeningController extends VBox implements OefeningObserver {

    private DomeinControllerOefening dc;
    private FrameOefeningController fc;
    private FileChooser fileChooserOpgave;
    private FileChooser fileChooserFeedback;
    private File fileOpgave;
    private File fileFeedback;
    private String fileOpgaveNaam;
    private String fileFeedbackNaam;
    
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
    @FXML private Label lblError;
    @FXML private Label lblSuccess;

    public DetailPanelOefeningController(DomeinControllerOefening dcon, FrameOefeningController fc) {
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
        this.fc = fc; // parent controller for showing listview bewerkingen en doelstellingen
        
        this.fileChooserOpgave = new FileChooser();
        fileChooserOpgave.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        this.fileChooserFeedback = new FileChooser();
        fileChooserFeedback.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        
        ddlVak.setItems(dc.geefVakken());
        initButtons(true);
    }
    
    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);
        btnOpenOpgave.setDisable(isNew);
        btnOpenFeedback.setDisable(isNew);
    }

    void initNieuweOefening() {
        initButtons(true);
        clearRender();
    }
    
    private void clearRender() {
        dc.setListDoelstellingenVanOefening(new ArrayList<>());
        dc.setListGroepsbewerkingenVanOefening(new ArrayList<>());

        initButtons(true);
        txfNaam.setText("");
        txfAntwoord.setText("");
        ddlVak.setItems(dc.geefVakken());
        ddlVak.getSelectionModel().clearSelection();
        fileOpgave = null;
        fileFeedback = null;
        lblOpgave.setText("");
        lblFeedback.setText("");
        lblGroepsbewerkingenCount.setText("0 bewerkingen geselecteerd");
        lblDoelstellingenCount.setText("0 doelstellingen geselecteerd");
        lblError.setText("");
        lblSuccess.setText("");
        txfNaam.requestFocus();
    }

    @Override
    public void update(IOefening o) {
        initButtons(false);
        txfNaam.setText(o.getNaam());
        txfAntwoord.setText(o.getAntwoord());
        ddlVak.setItems(dc.geefVakken());
        ddlVak.getSelectionModel().select(o.getVak());
        /*fileOpgave = o.getOpgave().getFile();   // dit werkt wel maar er is iets fout met de extensies.
        fileFeedback = o.getFeedback().getFile();*/
        fileOpgave = new File(PDF.FOLDERLOCATIE + o.getOpgave().getName());
        fileFeedback = new File(PDF.FOLDERLOCATIE + o.getFeedback().getName());
        lblOpgave.setText(o.getOpgave().getName());
        lblFeedback.setText(o.getFeedback().getName());
        lblGroepsbewerkingenCount.setText(o.getGroepsBewerkingen().size() + " bewerkingen geselecteerd");
        lblDoelstellingenCount.setText(o.getDoelstellingen().size() + " doelstellingen geselecteerd");
        lblError.setText("");
        lblSuccess.setText("");
    }

    @Override
    public void update(List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {
        System.out.println("In update van groepsbewerkingen");
        System.out.println(dc.getHuidigeOefening().getGroepsBewerkingen().size());
        lblGroepsbewerkingenCount.setText(dc.getHuidigeOefening().getGroepsBewerkingen().size() + " bewerkingen geselecteerd");
        lblDoelstellingenCount.setText(dc.getHuidigeOefening().getDoelstellingen().size() + " doelstellingen geselecteerd");
    }

    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
        clearRender();
    }

    @FXML
    private void btnOpenOpgaveOnAction(ActionEvent event) {
        try {
            if (fileOpgave.toString().endsWith(".pdf")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileOpgave);
            } else {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(fileOpgave);
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    @FXML
    private void btnFileOpgaveOnAction(ActionEvent event) {
        fileOpgave = fileChooserOpgave.showOpenDialog((Stage)(this.getScene().getWindow()));
        if (fileOpgave != null) {
            fileOpgaveNaam = fileOpgave.getName();
            lblOpgave.setText(fileOpgaveNaam);
            btnOpenOpgave.setDisable(false);
        }
    }

    @FXML
    private void btnOpenFeedbackOnAction(ActionEvent event) {
        try {
            if (fileFeedback.toString().endsWith(".pdf")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileFeedback);
            } else {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(fileFeedback);
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    @FXML
    private void btnFileFeedbackOnAction(ActionEvent event) {
        fileFeedback = fileChooserFeedback.showOpenDialog((Stage)(this.getScene().getWindow()));
        if (fileFeedback != null) {
            lblFeedback.setText(fileFeedback.getName());
            btnOpenFeedback.setDisable(false);
        }
    }

    @FXML
    private void btnGroepsbewerkingenOnAction(ActionEvent event) {
        fc.toonListview("gbw");
    }

    @FXML
    private void btnDoelstellingenOnAction(ActionEvent event) {
        fc.toonListview("doels");
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        try {
            dc.voegNieuweOefeningToe(txfNaam.getText(), txfAntwoord.getText(), fileOpgave, fileFeedback, ddlVak.getSelectionModel().getSelectedItem());

            clearRender();
            lblError.setText("");
            lblSuccess.setText("De oefening werd succesvol toegevoegd.");
            fc.toonListview("cancel/init");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
        try {
            dc.pasOefeningAan(txfNaam.getText(), txfAntwoord.getText(), fileOpgave, fileFeedback, ddlVak.getSelectionModel().getSelectedItem());
            lblError.setText("");
            lblSuccess.setText("De oefening werd succesvol aangepast.");
            fc.toonListview("cancel/init");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }
    
}