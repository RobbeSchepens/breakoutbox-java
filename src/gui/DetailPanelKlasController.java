/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.KlasController;
import domein.Leerling;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelKlasController extends VBox {

    KlasController kc;

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

    }


    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
    }

    @FXML
    private void btnVoegLlnToeOnAction(ActionEvent event) {
    }

    @FXML
    private void btnUploadExcelOnAction(ActionEvent event) {
    }

}
