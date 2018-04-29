/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.BoxController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelBoxController extends VBox {
    FrameBoxController fc;
    BoxController bc;

    @FXML
    private Label lblTitleRight;

    @FXML
    private Button btnNieuweOefening;
    @FXML
    private Button btnOefeningen;

    @FXML
    private Label lblOefeningenCount;
    @FXML
    private Button btnToegangscodes;
    @FXML
    private Label lblToegangscodesCount;
    @FXML
    private Label lblFeedback;

    @FXML
    private Button btnActies;
    @FXML
    private Label lblActiesCount;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;

    @FXML
    private Label lblError;
    @FXML
    private Label lblSuccess;

    public DetailPanelBoxController(BoxController bc, FrameBoxController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanelBox.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.bc = bc;
        this.fc = fc;

    }


    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
    }

    @FXML
    private void btnOefeningenOnAction(ActionEvent event) {
    }

    @FXML
    private void btnToegangscodesOnAction(ActionEvent event) {
    }

    @FXML
    private void btnActiesonAction(ActionEvent event) {
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
    }

}
