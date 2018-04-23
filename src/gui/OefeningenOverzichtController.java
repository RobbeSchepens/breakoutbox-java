package gui;

import domein.Doelstelling;
import domein.DomeinController;
import domein.Groepsbewerking;
import domein.IOefening;
import domein.OefeningObserver;
import domein.PDF;
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
    private Label lblAantalGroepsbewerkingenGeselecteerd;
    @FXML
    private Label lblAantalDoelstellingenGeselecteerd;

    @FXML
    private Label lblOpgavePadNaam;
    @FXML
    private Label lblFeedbackPadNaam;
    @FXML
    private Button btnaddOpgave;
    @FXML
    private Button btnaddFeedback;
    @FXML
    private Button btnOpgavePreview;
    @FXML
    private Button btnFeedbackPreview;
    @FXML
    private Button btnSwitchNaarMaakNieuweOefening;
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
    private List<Doelstelling> doelstellingen;
    private List<Groepsbewerking> groepsbewerkingen;

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
        dc.laadOefeningen();
        /*
        ddlVakkenFilter.setItems(FXCollections.observableArrayList(new Vak("Vak1Test"), new Vak("Vak2Test")));
        ddlVakkenFilter.setItems(FXCollections.observableArrayList(new Vak("Vak1Test"), new Vak("Vak2Test")));
         */

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
    public void update(String naam, String antwoord, Vak vak, List<Groepsbewerking> groepsbewerkings, List<Doelstelling> doelstellingen) {
        this.naam = naam;
        this.antwoord = antwoord;
        this.vak = vak;

        display();
    }

    public void display() {

        tbvOefeningen.setItems(dc.geefOefeningen());
    }

    private void setAllItems() {

        btnSwitchNaarMaakNieuweOefening.visibleProperty().setValue(false);
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
        //groepsbewerkingen beschikbaar
        lsvBeschikbareBewerkingen.setItems(dc.geefGroepsbewerkingen());
        setLblListViewGroepsbewerkingen();
        lsvBeschikbareBewerkingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Groepsbewerking gbw = lsvBeschikbareBewerkingen.getSelectionModel().getSelectedItem();
                if (!(lsvGeselecteerdeBewerkingen.getItems().size() >= 10)) {
                    lsvGeselecteerdeBewerkingen.getItems().add(gbw);
                    //lsvBeschikbareBewerkingen.getItems().remove(gbw); Deze geeft error (maar geeft niet)
                    setLblListViewGroepsbewerkingen();

                } else {
                    System.out.println("Je kan niet meer dan 10 selecteren");
                }
            }
        });
        //groepsbewerkingen geselecteerd
        lsvGeselecteerdeBewerkingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Groepsbewerking gbw = lsvGeselecteerdeBewerkingen.getSelectionModel().getSelectedItem();
                //lsvBeschikbareBewerkingen.getItems().add(gbw);
                //lsvGeselecteerdeBewerkingen.getItems().remove(gbw);
                setLblListViewGroepsbewerkingen();
            }
        });

        //doelstellingen alle
        lsvBeschikbareDoelstellingen.setItems(dc.geefDoelstellingen());
        setLblListViewDoelstellingen();
        lsvBeschikbareDoelstellingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Doelstelling dsl = lsvBeschikbareDoelstellingen.getSelectionModel().getSelectedItem();
                lsvGeselecteerdeDoelstellingen.getItems().add(dsl);
                //lsvBeschikbareBewerkingen.getItems().remove(gbw); Deze geeft error (maar geeft niet)
                setLblListViewDoelstellingen();
            }
        });

        //doelstellingen geselecteerd
        lsvGeselecteerdeDoelstellingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Doelstelling gbw = lsvGeselecteerdeDoelstellingen.getSelectionModel().getSelectedItem();

                //lsvGeselecteerdeDoelstellingen.getItems().add(gbw);
                //lsvGeselecteerdeDoelstellingen.getItems().remove(gbw);
                setLblListViewDoelstellingen();
            }
        });

    }

    private void displayHuidigeOefening() {
        btnSwitchNaarMaakNieuweOefening.visibleProperty().setValue(true);

        btnaddOpgave.setText("Replace");
        btnOpgavePreview.disableProperty().setValue(false);
        btnaddFeedback.setText("Replace");
        btnFeedbackPreview.disableProperty().setValue(false);

        lblToevOfBewerken.setText("Oefening Bewerken");
        btnVoegOefeningToe.setText("Bewerk");

        IOefening oefUitDc = dc.getHuidigeOefening();
        txfNaam.setText(oefUitDc.getNaam());
        txfAntwoord.setText(oefUitDc.getAntwoord());
        ddlVakken.getSelectionModel().select(oefUitDc.getVak());

        System.out.println(oefUitDc.getGroepsBewerkingen()); //werkt nog niet bij nieuwe oefening maken
        System.out.println(oefUitDc.getDoelstellingen());

        //// werkt nog niet
        lsvGeselecteerdeBewerkingen.setItems(FXCollections.observableArrayList(oefUitDc.getGroepsBewerkingen()));
        lsvGeselecteerdeDoelstellingen.setItems(FXCollections.observableArrayList(oefUitDc.getDoelstellingen()));
        ////
        lblFeedbackPadNaam.setText(oefUitDc.getOpgave().getName());
        lblOpgavePadNaam.setText(oefUitDc.getFeedback().getName());

        String pathNaarOef = PDF.FOLDERLOCATIE;

        File opgaveHuidig = new File(pathNaarOef + oefUitDc.getOpgave());
        File feedbackHuidig = new File(pathNaarOef + oefUitDc.getFeedback());

        this.opgaveChooser = new FileChooser();
        opgaveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        this.feedbackChooser = new FileChooser();
        feedbackChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        opgave = opgaveHuidig;
        feedback = feedbackHuidig;

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
        /*dc.verwijderOef(tbvOefeningen.getSelectionModel().getSelectedItem().getNaam());

        // dit weg ?
        tbvOefeningen.setItems(dc.geefOefeningen());
        colNaam.setCellValueFactory(v -> v.getValue().naamProperty());
        colVak.setCellValueFactory(v -> v.getValue().getVak().naamProperty());*/
        resetScherm();
    }

    @FXML
    private void btnSwitchNaarMaakNieuweOefeningOnAction(ActionEvent event) {

        resetScherm();
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
        // als oefening in bob zit dan bewerk knop (btnVoegOefeningToeOnAction) disabelen (je mag niet bewerken als ie in een bob zit)

        btnSwitchNaarMaakNieuweOefening.disableProperty().setValue(false);

        try {
            dc.bewerkOefening(txfNaam.getText(),
                    ddlVakken.getValue(),
                    opgave,
                    lsvGeselecteerdeBewerkingen.getItems(),
                    txfAntwoord.getText(),
                    feedback,
                    lsvGeselecteerdeDoelstellingen.getItems()
            );
        } catch (NumberFormatException ex) {
            System.out.println("vul een getal in");
        } catch (IllegalArgumentException ex) {
            System.out.println("illegal exception in oefeningbewerken: " + ex);
        }
    }

    private void resetScherm() {
        tbvOefeningen.getSelectionModel().clearSelection();
        btnSwitchNaarMaakNieuweOefening.visibleProperty().setValue(false);
        btnVoegOefeningToe.setText("Voeg oefening toe");
        btnaddOpgave.setText("Add file");
        btnaddFeedback.setText("Add file");
        lblToevOfBewerken.setText("Oefening Toevoegen");
        txfNaam.setText("");
        txfAntwoord.setText("");
        ddlVakken.getSelectionModel().clearSelection();
        lsvGeselecteerdeBewerkingen.getItems().clear();
        lsvGeselecteerdeDoelstellingen.getItems().clear();
        lblFeedbackPadNaam.setText("");
        lblOpgavePadNaam.setText("");
        lblAantalGroepsbewerkingenGeselecteerd.setText("");
        lblAantalDoelstellingenGeselecteerd.setText("");

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

    private void setLblListViewGroepsbewerkingen() {
        int sizeList = lsvGeselecteerdeBewerkingen.getItems().size();
        if (sizeList == 0) {
            lblAantalGroepsbewerkingenGeselecteerd.setText("");
        } else if (sizeList == 1) {
            lblAantalGroepsbewerkingenGeselecteerd.setText("Er is " + sizeList + " bewerking geselcteerd");
        } else {
            lblAantalGroepsbewerkingenGeselecteerd.setText("Er zijn " + sizeList + " bewerkingen geselcteerd");
        }

    }

    private void setLblListViewDoelstellingen() {
        int sizeList = lsvGeselecteerdeDoelstellingen.getItems().size();
        if (sizeList == 0) {
            lblAantalDoelstellingenGeselecteerd.setText("");
        } else if (sizeList == 1) {
            lblAantalDoelstellingenGeselecteerd.setText("Er is " + sizeList + " doelstelling geselcteerd");
        } else {
            lblAantalDoelstellingenGeselecteerd.setText("Er zijn " + sizeList + " doelstelling geselcteerd");
        }
    }

}
