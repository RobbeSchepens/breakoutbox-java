package gui;

import domein.BoxController;
import domein.DomeinControllerOefening;
import domein.KlasController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenubarController extends HBox{

    private static EnumMenu nextpage = EnumMenu.HOME;
    private DomeinControllerOefening dc;
    private BoxController bc;
    private KlasController kc;
    @FXML private HBox hbxHome;
    @FXML private HBox hbxOef;
    @FXML private HBox hbxBoxes;
    @FXML private HBox hbxSessies;
    @FXML private HBox hbxActies;
    @FXML private HBox hbxKlassen;
    
    public MenubarController(DomeinControllerOefening dcon, KlasController kc, BoxController bc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("Menubar.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.dc = dcon;
        this.bc = bc;
        this.kc = kc;
        
        String nonactiveCss = "-fx-background-color: white; -fx-border-color: #ccc;";
        String nonactiveCssLabel = "-fx-text-fill: #333;";
        String activeCss = "-fx-background-color: #006fe6;";
        String activeCssLabel = "-fx-text-fill: white;";
        
        hbxHome.setStyle(nonactiveCss);
        hbxHome.getChildren().get(0).setStyle(nonactiveCssLabel);
        hbxOef.setStyle(nonactiveCss);
        hbxOef.getChildren().get(0).setStyle(nonactiveCssLabel);
        hbxBoxes.setStyle(nonactiveCss);
        hbxBoxes.getChildren().get(0).setStyle(nonactiveCssLabel);
        hbxSessies.setStyle(nonactiveCss);
        hbxSessies.getChildren().get(0).setStyle(nonactiveCssLabel);
        hbxActies.setStyle(nonactiveCss);
        hbxActies.getChildren().get(0).setStyle(nonactiveCssLabel);
        hbxKlassen.setStyle(nonactiveCss);
        hbxKlassen.getChildren().get(0).setStyle(nonactiveCssLabel);
        
        switch (nextpage) {
            case HOME: 
                hbxHome.setStyle(activeCss);
                hbxHome.getChildren().get(0).setStyle(activeCssLabel); break;
            case OEFENING: 
                hbxOef.setStyle(activeCss);
                hbxOef.getChildren().get(0).setStyle(activeCssLabel); break;
            case BOX:
                hbxBoxes.setStyle(activeCss);
                hbxBoxes.getChildren().get(0).setStyle(activeCssLabel); break;
            case SESSIE:
                hbxSessies.setStyle(activeCss);
                hbxSessies.getChildren().get(0).setStyle(activeCssLabel); break;
            case ACTIE:
                hbxActies.setStyle(activeCss);
                hbxActies.getChildren().get(0).setStyle(activeCssLabel); break;
            case KLAS:
                hbxKlassen.setStyle(activeCss);
                hbxKlassen.getChildren().get(0).setStyle(activeCssLabel); break;
        }
    } 
    
    @FXML
    private void hbxHomeOnMouseClicked(MouseEvent event) {
        if (dc == null) { // gebeurt nooit momenteel
            new DomeinControllerOefening();
        }

        nextpage = EnumMenu.HOME;
        FrameHomeController sc = new FrameHomeController(dc, kc, bc);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxOefOnMouseClicked(MouseEvent event) {
        if (dc == null) {
            System.out.println("OefeningController was null and is being initialized.");
            dc = new DomeinControllerOefening();
        }
        nextpage = EnumMenu.OEFENING;
        FrameOefeningController sc = new FrameOefeningController(dc, kc, bc);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxBoxesOnMouseClicked(MouseEvent event) {
        if (bc == null) {
            System.out.println("BoxController was null and is being initialized.");
            bc = new BoxController();
        }
        nextpage = EnumMenu.BOX;
        FrameBoxController sc = new FrameBoxController(dc, kc, bc);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxSessiesOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void hbxhbxActiesOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void hbxKlassenOnMouseClicked(MouseEvent event) {
        if (kc == null) {
            System.out.println("KlasController was null and is being initialized.");
            kc = new KlasController();
        }
        nextpage = EnumMenu.KLAS;
        FrameKlassenController sc = new FrameKlassenController(dc, kc, bc);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }
    
}