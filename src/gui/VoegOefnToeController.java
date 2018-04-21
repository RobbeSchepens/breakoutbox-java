package gui;

import domein.DomeinController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VoegOefnToeController extends AnchorPane {

    private DomeinController dc;

    public VoegOefnToeController(DomeinController dc) {
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
        OefeningenOverzichtController sc = new OefeningenOverzichtController(dc);
        Scene scene = new Scene(sc, 1280, 720);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage)this.getScene().getWindow()).setScene(scene);
    }
}