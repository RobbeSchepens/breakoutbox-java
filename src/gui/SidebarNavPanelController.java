package gui;

import domein.DomeinController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SidebarNavPanelController extends VBox {

    private DomeinController dc;
    @FXML private HBox hbxOef;
    @FXML private HBox hbxSessies;
    @FXML private HBox hbxBoxes;
    @FXML private HBox hbxHome;

    public SidebarNavPanelController(DomeinController dcon) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("SidebarNavPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dcon;
    }  

    @FXML
    private void hbxOefOnMouseClicked(MouseEvent event) {
        FrameOefeningController sc = new FrameOefeningController(dc);
        Scene scene = new Scene(sc, 1430, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
        hbxOef.setStyle(".activenav");
        hbxSessies.setStyle("");
        hbxBoxes.setStyle("");
    }

    @FXML
    private void hbxSessiesOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void hbxBoxesOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void hbxHomeOnMouseClicked(MouseEvent event) {
        HoofdMenuController sc = new HoofdMenuController(dc);
        Scene scene = new Scene(sc, 1430, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }
}