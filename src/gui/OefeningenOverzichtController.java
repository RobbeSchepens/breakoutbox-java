/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningController;
import domein.Vak;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daan
 */
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
        HoofdMenuController mainMenuVC = new HoofdMenuController();
        Stage stage = (Stage) (this.getScene().getWindow());
        Scene scene = new Scene(mainMenuVC);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnVoegOefeningToeOnAction(ActionEvent event) {
        VoegOefnToeController oefnToevoegenVC = new VoegOefnToeController(dc);
        Stage stage = (Stage) (this.getScene().getWindow());
        Scene scene = new Scene(oefnToevoegenVC);
        stage.setScene(scene);
        stage.show();
    }

}