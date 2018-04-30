/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.BoxController;
import domein.BoxObserver;
import domein.IBox;
import domein.IKlas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelBoxListOefeningenController extends VBox implements BoxObserver {
     
     
    FrameBoxController fc;
    BoxController bc;

    @FXML
    private Label lblTitleLeftList;
    @FXML
    private Label lblAantalGeselecteerd;
    @FXML
    private ListView<?> lsvListAlle;
    @FXML
    private ListView<?> lsvListGeselecteerde;
    @FXML
    private Button btnDeselectAll;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;

    /**
     * Initializes the controller class.
     */
    public DetailPanelBoxListOefeningenController(BoxController bc, FrameBoxController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanelBoxListOefeningen.fxml"));
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

    @Override
    public void update(IBox box) {
        System.out.println("update");
    }

    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void btnDeselectAllOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
    }


}
