package gui;

import domein.DomeinController;
import domein.IOefening;
import domein.OefeningObserver;
import domein.Vak;
import exceptions.SpecialeTekensInNaamException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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

    private DomeinController dc;
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

    public DetailPanelOefeningController(DomeinController dcon, FrameOefeningController fc) {
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
        btnNieuweOefening.setVisible(!isNew);
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);
        btnOpenOpgave.setDisable(isNew);
        btnOpenFeedback.setDisable(isNew);
    }
    
    private void clearRender() {
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
    }

    @Override
    public void update(IOefening o) {
        initButtons(false);
        txfNaam.setText(o.getNaam());
        txfAntwoord.setText(o.getAntwoord());
        ddlVak.setItems(dc.geefVakken());
        ddlVak.getSelectionModel().select(o.getVak());
        fileOpgave = null;
        fileFeedback = null;
        lblOpgave.setText(o.getOpgave().getName());
        lblFeedback.setText(o.getFeedback().getName());
        lblGroepsbewerkingenCount.setText(o.getGroepsBewerkingen().size() + " bewerkingen geselecteerd");
        lblDoelstellingenCount.setText(o.getDoelstellingen().size() + " doelstellingen geselecteerd");
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
        fc.toonListview(true);
    }

    @FXML
    private void btnDoelstellingenOnAction(ActionEvent event) {
        fc.toonListview(true);
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        try {
            dc.voegNieuweOefeningToe(
                    txfNaam.getText(),
                    txfAntwoord.getText(),
                    ddlVak.getSelectionModel().getSelectedItem(),
                    fileOpgave,
                    fileOpgaveNaam,
                    fileFeedback,
                    fileFeedbackNaam,
                    /*lsvGeselecteerdeBewerkingen.getItems()*/ null,
                    /*lsvGeselecteerdeDoelstellingen.getItems()*/ null
            );
            clearRender();
            lblSuccess.setText("De oefening werd succesvol toegevoegd.");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
        try {
            dc.pasOefeningAan(
                    txfNaam.getText(),
                    txfAntwoord.getText(),
                    ddlVak.getSelectionModel().getSelectedItem(),
                    fileOpgave,
                    fileOpgaveNaam,
                    fileFeedback,
                    fileFeedbackNaam,
                    /*lsvGeselecteerdeBewerkingen.getItems()*/ null,
                    /*lsvGeselecteerdeDoelstellingen.getItems()*/ null
            );
            lblSuccess.setText("De oefening werd succesvol aangepast.");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException ex) {
            lblError.setText(ex.getMessage());
        }
    }
    
}