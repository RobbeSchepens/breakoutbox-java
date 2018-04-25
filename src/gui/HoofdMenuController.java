package gui;

import domein.DomeinController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HoofdMenuController extends GridPane {
    
    private DomeinController dc;
    @FXML private Button btnBeheerBob;
    @FXML private Button btnBeheerSessies;
    @FXML private Button btnBeheerOef;
    @FXML private Label lblAantalOef;
    @FXML private Label lblAantalBoxes;
    @FXML private Label lblAantalSessies;
    @FXML private Button btnNieuweOefView;
    
    public HoofdMenuController(DomeinController dc) {
        this.dc = dc;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HoofdMenu.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        laadAantallen();
    }

    @FXML
    private void btnBeheerOefOnAction(ActionEvent event) {
        FrameOefeningController sc = new FrameOefeningController(dc);
        Scene scene = new Scene(sc, 1430, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    private void laadAantallen() {
        lblAantalOef.setText(String.valueOf(dc.geefAantalOefeningen()));
    }

    @FXML
    private void btnNieuweOefViewOnAction(ActionEvent event) {
        OefeningenOverzichtController sc = new OefeningenOverzichtController(dc);
        Scene scene = new Scene(sc, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }
}
