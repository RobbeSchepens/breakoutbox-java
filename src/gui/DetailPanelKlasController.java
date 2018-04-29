/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Leerling;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelKlasController implements Initializable {

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
