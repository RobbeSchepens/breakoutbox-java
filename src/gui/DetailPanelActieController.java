/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.ActieController;
import domein.ActieObserver;
import domein.IActie;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelActieController extends VBox implements ActieObserver {
    private ActieController ac;
    private FrameActieController fc;

    @FXML
    private Label lblTitleRight;
    @FXML
    private Button btnNieuweOefening;
    @FXML
    private TextField txfNaam;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Label lblError;
    @FXML
    private Label lblSuccess;

    /**
     * Initializes the controller class.
     */

    public DetailPanelActieController(ActieController ac, FrameActieController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("DetailPanelActie.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.ac = ac;
        this.fc = fc;
        initButtons(true);
    }

    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);

    }
    @Override
    public void update(IActie actie) {

        clearRender();
        txfNaam.setText(actie.toString());
        initButtons(false);
    }
    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {

        try {
            ac.voegActieToe(txfNaam.getText());
            clearRender();
            lblError.setText("");
            lblSuccess.setText("De Actie werd succesvol toegevoegd.");

        } catch (IllegalArgumentException ex) {
            lblSuccess.setText("");
            System.out.println(ex.getMessage());
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {

        try {
            ac.pasActieAan(txfNaam.getText());
            clearRender();
            lblError.setText("");
            lblSuccess.setText("De Actie werd succesvol aangepast.");
        } catch (IllegalArgumentException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }

    void initNieuweOefening() {
        initButtons(true);
        clearRender();

    }

    private void clearRender() {
        initButtons(true);
        txfNaam.setText("");
    }

}
