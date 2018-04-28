package gui;

import domein.DomeinControllerOefening;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HoofdMenuController extends GridPane {
    
    private DomeinControllerOefening dc;
    @FXML private Button btnBeheerBob;
    @FXML private Button btnBeheerSessies;
    @FXML private Button btnBeheerOef;
    @FXML private Label lblAantalOef;
    @FXML private Label lblAantalBoxes;
    @FXML private Label lblAantalSessies;
    
    public HoofdMenuController(DomeinControllerOefening dc) {
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

    private void laadAantallen() {
        lblAantalOef.setText(String.valueOf(dc.geefAantalOefeningen()));
        //lblAantalBoxes.setText(String.valueOf(dc.geefAantalBoxes()));
        //lblAantalSessies.setText(String.valueOf(dc.geefAantalSessies()));
    }

    @FXML
    private void btnBeheerOefOnAction(ActionEvent event) {
        /* FrameOefeningController sc = new FrameOefeningController(dc);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);*/
    }

    @FXML
    private void btnBeheerBobOnAction(ActionEvent event) {
        /* FrameBoxController sc = new FrameBoxController(dc);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);*/
    }

    @FXML
    private void btnBeheerSessiesOnAction(ActionEvent event) {
//        FrameSessiesController sc = new FrameSessiesController(dc);
//        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
//        scene.getStylesheets().add("gui/css/style.css");
//        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

}
