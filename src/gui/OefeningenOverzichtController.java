package gui;

import domein.DomeinController;
import domein.IOefening;
import domein.OefeningObserver;
import domein.Oefening;
import domein.Vak;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OefeningenOverzichtController extends AnchorPane implements OefeningObserver {

    //FXML
    @FXML
    private TableColumn<Oefening, String> colNaam;
    @FXML
    private TableColumn<Oefening, String> colVak;
    @FXML
    private Label lblOpgavePadNaam;
    @FXML
    private Label lblFeedbackPadNaam;
    @FXML
    private Button btnOpgavePreview;
    @FXML
    private Button btnFeedbackPreview;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ChoiceBox<Vak> ddlVakkenFilter;
    @FXML
    private TableView<IOefening> tbvOefeningen;
    @FXML
    private TextField txfNaam;
    @FXML
    private ChoiceBox<Vak> ddlVakken;
    @FXML
    private TextField txfAntwoord;
    @FXML
    private Button btnAddOefening;
    @FXML
    private Button btnVerwijderOefening;

    //Attributes
    //doelstellingen ??
    //bewerkingen??
    private DomeinController dc;
    private List<Vak> vakkenlijst;
    private List<Vak> vakkenlijstFilter;
    private FileChooser opgaveChooser;
    private FileChooser feedbackChooser;
    private File opgave;
    private File feedback;
    
    // huidigeOefening update Observer
    private String naam;
    private String antwoord;
    private Vak vak;

    public OefeningenOverzichtController(DomeinController dc) {
        //scene loaden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OefeningenOverzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dc;

        tbvOefeningen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                dc.setHuidigeOefening(newValue);
                //dc.addOefeningObserver(this); // oorzaak van IndexOutOfBoundsException ...
                displayHuidigeOefening();
            }
        });
        
        setAllItems();
        
        
        
        //init file chooser
        this.opgaveChooser = new FileChooser();
        opgaveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        this.feedbackChooser = new FileChooser();
        feedbackChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        //default layout
        btnOpgavePreview.setDisable(true);
        btnFeedbackPreview.setDisable(true);
    }

    @FXML
    private void btnHoofdmenuOnAction(ActionEvent event) {
        HoofdMenuController sc = new HoofdMenuController(dc);
        Scene scene = new Scene(sc, 1280, 720);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void addOpgaveOnAction(ActionEvent event) {

        opgave = opgaveChooser.showOpenDialog((Stage) (this.getScene().getWindow()));
        if (opgave != null) {
            lblOpgavePadNaam.setText(opgave.getName());
            btnOpgavePreview.setDisable(false);
        } else {
            btnOpgavePreview.setDisable(true);
        }
    }

    @FXML
    private void addFeedbackOnAction(ActionEvent event) {
        feedback = feedbackChooser.showOpenDialog((Stage) (this.getScene().getWindow()));
        if (feedback != null) {
            lblFeedbackPadNaam.setText(feedback.getName());
            btnFeedbackPreview.setDisable(false);
        } else {
            btnFeedbackPreview.setDisable(true);
        }
    }

    @FXML
    private void btnOpgavePreviewOnAction(ActionEvent event) {
        try {
            if (opgave.toString().endsWith(".pdf")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + opgave);
            } else {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(opgave);
            }
        } catch (IOException ioe) {
            System.out.println("fout");
        }
    }

    @FXML
    private void btnFeedbackPreviewOnAction(ActionEvent event) {
        try {
            if (feedback.toString().endsWith(".pdf")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + feedback);
            } else {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(feedback);
            }
        } catch (IOException ioe) {
            System.out.println("fout");
        }
    }

    @Override
    public void update(String naam, String antwoord, Vak vak) {
        this.naam = naam;
        this.antwoord = antwoord;
        this.vak = vak;
        display();
    }

    public void display() {
        System.out.printf(naam +" "+ antwoord +" "+ vak.toString());
        tbvOefeningen.setItems(dc.geefOefeningen());
    }

    private void setAllItems() {
        tbvOefeningen.setItems(dc.geefOefeningen());
        colNaam.setCellValueFactory(v->v.getValue().naamProperty());
        colVak.setCellValueFactory(v->v.getValue().getVak().naamProperty());
        
        ddlVakken.setItems(dc.geefVakken());
        ddlVakkenFilter.setItems(dc.geefVakken());
    }

    private void displayHuidigeOefening() {
        txfNaam.setText(dc.getHuidigeOefening().getNaam());
        txfAntwoord.setText(dc.getHuidigeOefening().getAntwoord());
        ddlVakken.getSelectionModel().select(dc.getHuidigeOefening().getVak());
    }

    @FXML
    private void btnAddOefeningOnAction(ActionEvent event) {
        dc.voegNieuweOefeningToe(txfNaam.getText(), txfAntwoord.getText(), 
                ddlVakken.getSelectionModel().getSelectedItem());
        setAllItems();
    }

    @FXML
    private void btnVerwijderOefeningOnAction(ActionEvent event) {
    }

}