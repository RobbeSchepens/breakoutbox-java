package gui;

import domein.Oefening;
import domein.OefeningController;
import domein.Vak;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        // initialize
        this.dc = dc;

//      this.vakkenlijst = new ArrayList(dc.geefAlleVakken());
        this.vakkenlijstFilter = new ArrayList<>();
        Vak vakTemp = new Vak("Alles");
        vakkenlijstFilter.add(vakTemp);

//      vakFilterChoiceBox.addAll(vakkenlijst);
        vakFilterChoiceBox.getSelectionModel().select(0);
        vakFilterChoiceBox.setItems(FXCollections.observableArrayList(vakkenlijstFilter));
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
}
