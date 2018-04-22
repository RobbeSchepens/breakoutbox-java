package gui;

import domein.Doelstelling;
import domein.DomeinController;
import domein.Groepsbewerking;
import domein.IOefening;
import domein.OefeningObserver;
import domein.Vak;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OefeningenOverzichtController extends AnchorPane implements OefeningObserver {

    //FXML
    //tableview oefeningen
    @FXML
    private TableColumn<IOefening, String> colNaam;
    @FXML
    private TableColumn<IOefening, String> colVak;
    // listviews groepsbewerkingen
    @FXML
    private ListView<Groepsbewerking> lsvBeschikbareBewerkingen;
    @FXML
    private ListView<Groepsbewerking> lsvGeselecteerdeBewerkingen;
    // listviews doelstellingen
    @FXML
    private ListView<Doelstelling> lsvBeschikbareDoelstellingen;
    @FXML
    private ListView<Doelstelling> lsvGeselecteerdeDoelstellingen;

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
    private ChoiceBox<Vak> ddlVakken;
    @FXML
    private TableView<IOefening> tbvOefeningen;
    @FXML
    private TextField txfNaam;

    @FXML
    private TextField txfAntwoord;
    @FXML
    private Label lblToevOfBewerken;

    // @FXML
    // private Button btnBewerkOefening;
    @FXML
    private Button btnVoegOefeningToe;
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
        /*
        ddlVakkenFilter.setItems(FXCollections.observableArrayList(new Vak("Vak1Test"), new Vak("Vak2Test")));
        ddlVakkenFilter.setItems(FXCollections.observableArrayList(new Vak("Vak1Test"), new Vak("Vak2Test")));
         */

        System.out.println(new Vak("Vak1Test"));

        tbvOefeningen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
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
        lblToevOfBewerken.setText("Oefening Toevoegen");

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
        System.out.printf(naam + " " + antwoord + " " + vak.toString());
        tbvOefeningen.setItems(dc.geefOefeningen());
    }

    private void setAllItems() {

        tbvOefeningen.setItems(dc.geefOefeningen());
        colNaam.setCellValueFactory(v -> v.getValue().naamProperty());
        colVak.setCellValueFactory(v -> v.getValue().getVak().naamProperty());

        //ddlVakkenFilter opvullen + alles vanvoor
        ObservableList<Vak> vakkenListInSetAllItemsObservable = FXCollections.observableArrayList(dc.geefVakken());
        vakkenListInSetAllItemsObservable.add(0, new Vak("Alles"));
        ddlVakkenFilter.setItems(vakkenListInSetAllItemsObservable);
        ddlVakkenFilter.getSelectionModel().selectFirst();
        //ddlVakken opvullen
        ddlVakken.setItems(dc.geefVakken());

        //listviews
        //groepsbewerkingen
        lsvBeschikbareBewerkingen.setItems(dc.geefGroepsbewerkingen());
        lsvBeschikbareBewerkingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Groepsbewerking gbw = lsvBeschikbareBewerkingen.getSelectionModel().getSelectedItem();
                lsvGeselecteerdeBewerkingen.getItems().add(gbw);

                //lsvBeschikbareBewerkingen.getItems().remove(gbw); Deze geeft error (maar geeft niet)
            }

        });

        //doelstellingen
        lsvBeschikbareDoelstellingen.setItems(dc.geefDoelstellingen());
        lsvBeschikbareDoelstellingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Doelstelling dsl = lsvBeschikbareDoelstellingen.getSelectionModel().getSelectedItem();
                lsvGeselecteerdeDoelstellingen.getItems().add(dsl);

            }

        });
    }

    private void displayHuidigeOefening() {
        txfNaam.setText(dc.getHuidigeOefening().getNaam());
        txfAntwoord.setText(dc.getHuidigeOefening().getAntwoord());
        ddlVakken.getSelectionModel().select(dc.getHuidigeOefening().getVak());
    }

    //@FXML
    //private void btnAddOefeningOnAction(ActionEvent event) { // btnaddoe
    //dc.voegNieuweOefeningToe(txfNaam.getText(), txfAntwoord.getText(), ddlVakken.getSelectionModel().getSelectedItem());
    //setAllItems();
    //}
    @FXML
    private void btnVoegOefeningToeOnAction(ActionEvent event) {
        String lblText = lblToevOfBewerken.getText();

        switch (lblText) {
            case "Oefening Toevoegen":
                OefeningToevoegen();
                break;
            case "Oefening Bewerken":
                OefeningBewerken();
                break;
        }

    }

    @FXML
    private void btnVerwijderOefeningOnAction(ActionEvent event) {
        dc.verwijderOef(tbvOefeningen.getSelectionModel().getSelectedItem().getNaam());

        // dit weg ?
        tbvOefeningen.setItems(dc.geefOefeningen());
        colNaam.setCellValueFactory(v -> v.getValue().naamProperty());
        colVak.setCellValueFactory(v -> v.getValue().getVak().naamProperty());
        //resetScherm();
    }

    private void OefeningToevoegen() {
        try { //naam, vak, opgave, groepsbewerkingen, antwoord, feedback, doelstelling);
            dc.voegNieuweOefeningToe(
                    txfNaam.getText(),
                    ddlVakken.getValue(),
                    opgave,
                    lsvGeselecteerdeBewerkingen.getItems(),
                    txfAntwoord.getText(),
                    feedback,
                    lsvGeselecteerdeDoelstellingen.getItems()
            );
            resetScherm();

            tbvOefeningen.setItems(dc.geefOefeningen());
            colNaam.setCellValueFactory(v -> v.getValue().naamProperty());
            colVak.setCellValueFactory(v -> v.getValue().getVak().naamProperty());

        } catch (NumberFormatException ex) {
            System.out.println("exception oefening toevoegen: antwoord moet een getal zijn");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }
    }

    private void OefeningBewerken() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void resetScherm() {
        lblToevOfBewerken.setText("Oefening Toevoegen");
        txfNaam.setText("");
        txfAntwoord.setText("");
        ddlVakken.getSelectionModel().clearSelection();
        lsvGeselecteerdeBewerkingen.getItems().clear();
        lsvGeselecteerdeDoelstellingen.getItems().clear();
        lblFeedbackPadNaam.setText("");
        lblOpgavePadNaam.setText("");

        //opgave chooser reset
        opgaveChooser = new FileChooser();
        opgaveChooser.setTitle("Open Opgave Bestand");
        opgaveChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        opgaveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        //feedback chooser reset
        feedbackChooser = new FileChooser();
        feedbackChooser.setTitle("Open Feedback Bestand");
        feedbackChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        feedbackChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        lblOpgavePadNaam.setText("");
        lblFeedbackPadNaam.setText("");

        btnFeedbackPreview.disableProperty().setValue(true);
        btnOpgavePreview.disableProperty().setValue(true);
        opgave = null;
        feedback = null;

    }

}
