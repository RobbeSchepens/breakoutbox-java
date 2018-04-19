package gui;

import domein.Oefening;
import domein.OefeningController;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OefeningenOverzichtController extends AnchorPane {

    //FXML
    @FXML
    private TableView<Oefening> oefeningenTable;
    @FXML
    private TableColumn<Oefening, String> colNaam;
    @FXML
    private TableColumn<Oefening, Vak> colVak;
    @FXML
    private TableColumn<Oefening, String> colOmschrijving;
    @FXML
    private ChoiceBox<Vak> vakFilterChoiceBox;
    @FXML
    private ChoiceBox<Vak> vakKeuzeChoiceBox;
    @FXML
    private Label lblOpgavePadNaam;
    @FXML
    private Label lblFeedbackPadNaam;
    @FXML
    private Button btnOpgavePreview;
    @FXML
    private Button btnFeedbackPreview;

    //Attributes
    //doelstellingen ??
    //bewerkingen??
    private OefeningController dc;
    private List<Vak> vakkenlijst;
    private List<Vak> vakkenlijstFilter;
    private FileChooser opgaveChooser;
    private FileChooser feedbackChooser;
    private File opgave;
    private File feedback;

    public OefeningenOverzichtController(OefeningController dc) {
        //scene loaden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OefeningenOverzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //init file chooser
        this.opgaveChooser = new FileChooser();
        opgaveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        this.feedbackChooser = new FileChooser();
        feedbackChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        //default layout
        btnOpgavePreview.setDisable(true);
        btnFeedbackPreview.setDisable(true);

        // initialize
        this.dc = dc;

        // default "alles" in choise box
        this.vakkenlijst = new ArrayList(/*dc.geefAlleVakken()*/);
        this.vakkenlijstFilter = new ArrayList<>();

        vakkenlijstFilter.add(new Vak("Alles"));
        vakkenlijstFilter.addAll(vakkenlijst);
        // choice box links
        vakKeuzeChoiceBox.setItems(FXCollections.observableArrayList(vakkenlijstFilter));
        vakKeuzeChoiceBox.getSelectionModel().selectFirst();
        // choice box rechts
        vakFilterChoiceBox.setItems(FXCollections.observableArrayList(vakkenlijstFilter));
        vakFilterChoiceBox.getSelectionModel().selectFirst();

    }

    @FXML
    private void btnHoofdmenuOnAction(ActionEvent event) {
        HoofdMenuController sc = new HoofdMenuController();
        Scene scene = new Scene(sc, 1280, 720);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void btnVoegOefeningToeOnAction(ActionEvent event) {
        VoegOefnToeController sc = new VoegOefnToeController(dc);
        Scene scene = new Scene(sc, 1280, 720, Color.web("#ffffff"));
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

}
