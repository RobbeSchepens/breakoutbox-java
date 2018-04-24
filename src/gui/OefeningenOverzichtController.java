package gui;

import domein.Doelstelling;
import domein.DomeinController;
import domein.Groepsbewerking;
import domein.IOefening;
import domein.Oefening;
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
import javafx.scene.SceneAntialiasing;
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

public class OefeningenOverzichtController extends AnchorPane {

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
    private List<Groepsbewerking> groepsbewerkingenGeselecteerd = new ArrayList<>();
    private List<Doelstelling> doelstellingenGeselecteerd = new ArrayList<>();

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
        Scene scene = new Scene(sc, 1280, 720, false, SceneAntialiasing.BALANCED);
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
        groepsbewerkingen = dc.geefAlleBewerkingen();
        doelstellingen = dc.geefAlleDoelstellingen();

        lsvBeschikbareBewerkingen.setItems(FXCollections.observableArrayList(groepsbewerkingen));
        lsvBeschikbareDoelstellingen.setItems(FXCollections.observableArrayList(doelstellingen));

        setLblListViewGroepsbewerkingen();
        lsvBeschikbareBewerkingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (!(lsvGeselecteerdeBewerkingen.getItems().size() >= 10)) {
                    Groepsbewerking bew = lsvBeschikbareBewerkingen.getSelectionModel().getSelectedItem();
                    groepsbewerkingenGeselecteerd.add(bew);
                    lsvGeselecteerdeBewerkingen.setItems(FXCollections.observableArrayList(groepsbewerkingenGeselecteerd));
                    groepsbewerkingen.remove(bew);
                    lsvBeschikbareBewerkingen.setItems(FXCollections.observableArrayList(groepsbewerkingen));
                    setLblListViewGroepsbewerkingen();
                } else {
                    System.out.println("Je kan niet meer dan 10 selecteren");
                    ;
                }
            }
        });
        //groepsbewerkingen geselecteerd
        lsvGeselecteerdeBewerkingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Groepsbewerking gbw = lsvGeselecteerdeBewerkingen.getSelectionModel().getSelectedItem();
                groepsbewerkingen.add(gbw);
                lsvBeschikbareBewerkingen.setItems(FXCollections.observableArrayList(groepsbewerkingen));
                groepsbewerkingenGeselecteerd.remove(gbw);
                lsvGeselecteerdeBewerkingen.setItems(FXCollections.observableArrayList(groepsbewerkingenGeselecteerd));
                setLblListViewGroepsbewerkingen();
            }
        });
        //doelstellingen alle
        setLblListViewDoelstellingen();
        lsvBeschikbareDoelstellingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Doelstelling dls = lsvBeschikbareDoelstellingen.getSelectionModel().getSelectedItem();
                doelstellingenGeselecteerd.add(dls);
                lsvGeselecteerdeDoelstellingen.setItems(FXCollections.observableArrayList(doelstellingenGeselecteerd));
                doelstellingen.remove(dls);
                lsvBeschikbareDoelstellingen.setItems(FXCollections.observableArrayList(doelstellingen));
                setLblListViewDoelstellingen();
            }
        });
        //doelstellingen geselecteerd
        lsvGeselecteerdeDoelstellingen.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Doelstelling dls = lsvGeselecteerdeDoelstellingen.getSelectionModel().getSelectedItem();

                doelstellingen.add(dls);
                lsvBeschikbareDoelstellingen.setItems(FXCollections.observableArrayList(doelstellingen));
                doelstellingenGeselecteerd.remove(dls);
                lsvGeselecteerdeDoelstellingen.setItems(FXCollections.observableArrayList(doelstellingenGeselecteerd));

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

        groepsbewerkingenGeselecteerd = dc.getHuidigeOefening().getGroepsBewerkingen();
        doelstellingenGeselecteerd = dc.getHuidigeOefening().getDoelstellingen();

        lsvGeselecteerdeBewerkingen.setItems(FXCollections.observableArrayList(groepsbewerkingenGeselecteerd));
        lsvGeselecteerdeDoelstellingen.setItems(FXCollections.observableArrayList(doelstellingenGeselecteerd));

        lblFeedbackPadNaam.setText(oefUitDc.getOpgave().getName());
        lblOpgavePadNaam.setText(oefUitDc.getFeedback().getName());

        String pathNaarOef = PDF.FOLDERLOCATIE;

        File opgaveHuidig = new File(pathNaarOef + oefUitDc.getOpgave());
        System.out.println(pathNaarOef);
        System.out.println(oefUitDc.getOpgave());
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

        dc.verwijderOef(tbvOefeningen.getSelectionModel().getSelectedItem());

        tbvOefeningen.setItems(dc.geefOefeningen());

        resetScherm();

    }

    @FXML
    private void btnSwitchNaarMaakNieuweOefeningOnAction(ActionEvent event) {

        resetScherm();
    }

    private void OefeningToevoegen() {
        try {
            dc.voegNieuweOefeningToe(
                    txfNaam.getText(),
                    txfAntwoord.getText(),
                    opgave,
                    feedback,
                    ddlVakken.getValue(),
                    groepsbewerkingenGeselecteerd,
                    doelstellingenGeselecteerd
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
            dc.bewerkOefening(tbvOefeningen.getSelectionModel().getSelectedItem().getNaam(), txfNaam.getText(),
                    ddlVakken.getValue(),
                    opgave,
                    groepsbewerkingenGeselecteerd,
                    txfAntwoord.getText(),
                    feedback,
                    doelstellingenGeselecteerd
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
        lblFeedbackPadNaam.setText("");
        lblOpgavePadNaam.setText("");
        lblAantalGroepsbewerkingenGeselecteerd.setText("");
        lblAantalDoelstellingenGeselecteerd.setText("");

        //reset listviews
        groepsbewerkingenGeselecteerd = new ArrayList<>();
        groepsbewerkingen = dc.geefAlleBewerkingen();
        doelstellingenGeselecteerd = new ArrayList<>();
        doelstellingen = dc.geefAlleDoelstellingen();
        lsvBeschikbareBewerkingen.setItems(FXCollections.observableArrayList(groepsbewerkingen));
        lsvBeschikbareDoelstellingen.setItems(FXCollections.observableArrayList(doelstellingen));
        lsvGeselecteerdeBewerkingen.getItems().clear();
        lsvGeselecteerdeDoelstellingen.getItems().clear();

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
