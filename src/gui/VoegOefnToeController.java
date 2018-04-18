/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.OefeningController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class VoegOefnToeController extends AnchorPane {

    private OefeningController dc;

    public VoegOefnToeController(OefeningController dc) {
        this.dc = dc;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/VoegOefnToe.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void btnTerugNaarOverzichtOefnOnAction(ActionEvent event) {
        OefeningenOverzichtController oefLijstView = new OefeningenOverzichtController(dc);
        Stage stage = (Stage) (this.getScene().getWindow());
        Scene scene = new Scene(oefLijstView);
        stage.setScene(scene);
        stage.show();
    }

}
