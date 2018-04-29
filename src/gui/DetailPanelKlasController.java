/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.IKlas;
import domein.KlasController;
import domein.KlasObserver;
import domein.Leerling;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelKlasController extends VBox implements KlasObserver {

    KlasController kc;
    FrameKlassenController fc;
    private FileChooser fileChooserExcel;
    List<Leerling> listLeerlingenTempAlle;

    @FXML
    private Label lblTitleRight;
    @FXML
    private Button btnNieuweOefening;
    @FXML
    private TextField txfNaamKlas;
    @FXML
    private TextField txfNaamLln;
    @FXML
    private Label lblOpgave1;
    @FXML
    private Label lblUploadExcel;
    @FXML
    private TextField txfVoornaam;
    @FXML
    private ListView<Leerling> lsvLeerlingen;
    @FXML
    private Label lblOpgave;
    @FXML
    private Button btnFileOpgave;
    @FXML
    private Label lblExcelName;
    @FXML
    private Label lblError;
    @FXML
    private Label lblSuccess;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblGeselect;


    /**
     * Initializes the controller class.
     */
    public DetailPanelKlasController(KlasController kc, FrameKlassenController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("DetailPanelKlas.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.kc = kc;
        this.fc = fc;
        initButtons(true);

        lsvLeerlingen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Leerling>() {
            @Override
            public void changed(ObservableValue<? extends Leerling> observable, Leerling oldValue, Leerling newValue) {
                if (!(newValue == null)) {
                    lblGeselect.setText("Geselecteerd : " + newValue.toString());
                }
            }
        });

    }

    @Override
    public void update(IKlas klas) {
        initButtons(false);
        txfNaamKlas.setText(klas.getNaam());
        lsvLeerlingen.setItems(FXCollections.observableArrayList(klas.getLeerlingen()));
        lblTitleRight.setText("Overzicht Klas");
        lblUploadExcel.setVisible(false);
        btnFileOpgave.setVisible(false);
    }

    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);

    }


    @FXML
    private void btnVoegLlnToeOnAction(ActionEvent event) {
        Leerling ln = new Leerling(txfNaamLln.getText(), txfVoornaam.getText());
        if (listLeerlingenTempAlle.contains(ln)) {
            System.out.println("hetzelfde");
        } else {
            System.out.println("niet hetzelfde");
            lsvLeerlingen.getItems().add(ln);
        }
    }

    @FXML
    private void btnVerwijderLlnOnAction(ActionEvent event) {

        lsvLeerlingen.getItems().remove(lsvLeerlingen.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void btnUploadExcelOnAction(ActionEvent event) {
        System.out.println("implement excel opener");

        //lblExcelName
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {

    }

    void initNieuweOefening() {
        initButtons(true);
        clearRender();

    }

    private void clearRender() {
        initButtons(true);
        lsvLeerlingen.setItems(FXCollections.observableArrayList(new ArrayList<Leerling>()));
        txfNaamKlas.setText("");
        txfNaamLln.setText("");
        txfVoornaam.setText("");


    }

}
