package gui;

import domein.ActieController;
import domein.BoxController;
import domein.DomeinController;
import domein.OefeningController;
import domein.KlasController;
import domein.SessieController;
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NavigatieController extends HBox{

    static EnumNavigatie nextpage = EnumNavigatie.HOME;

    private final DomeinController dc;
    private OefeningController oc;
    private BoxController bc;
    private SessieController sc;
    private KlasController kc;
    private ActieController ac;
            
    @FXML private HBox hbxHome;
    @FXML private HBox hbxOef;
    @FXML private HBox hbxBoxes;
    @FXML private HBox hbxSessies;
    @FXML private HBox hbxActies;
    @FXML private HBox hbxKlassen;
    
    public NavigatieController(DomeinController dc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("Navigatie.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dc;
        this.oc = dc.getOc();
        this.bc = dc.getBc();
        this.sc = dc.getSc();
        this.kc = dc.getKc();
        this.ac = dc.getAc();
        
        setActiveMenuItem();
        setMouseHoverIconButtons();
    } 
    
    private void setActiveMenuItem() {
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
    
    private void setMouseHoverIconButtons() {
        this.getChildren().forEach(e -> {
            if (e instanceof HBox) {
                e.setOnMouseEntered((Event event) -> {
                    getScene().setCursor(Cursor.HAND);
                });
                e.setOnMouseExited((Event event) -> {
                    getScene().setCursor(Cursor.DEFAULT);
                });
            }
        });
    }
    
    @FXML
    private void hbxHomeOnMouseClicked(MouseEvent event) {
        if (oc == null) { // gebeurt nooit momenteel
            System.out.println("OefeningController was null and is being initialized.");
            oc = new OefeningController();
        }

        nextpage = EnumNavigatie.HOME;
        HomeFrameController fc = new HomeFrameController(dc);
        Scene scene = new Scene(fc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxOefOnMouseClicked(MouseEvent event) {
        if (oc == null) {
            System.out.println("OefeningController was null and is being initialized.");
            oc = new OefeningController();
        }
        nextpage = EnumNavigatie.OEFENING;
        OefeningFrameController fc = new OefeningFrameController(dc);
        Scene scene = new Scene(fc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxBoxesOnMouseClicked(MouseEvent event) {
        if (bc == null || ac == null) {
            System.out.println("BoxController/ActieController was null and is being initialized.");
            bc = new BoxController();
            ac = new ActieController();
        }
        nextpage = EnumNavigatie.BOX;
        BoxFrameController fc = new BoxFrameController(dc);
        Scene scene = new Scene(fc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxSessiesOnMouseClicked(MouseEvent event) {
        if (sc == null) {
            System.out.println("SessieController was null and is being initialized.");
            sc = new SessieController();
        }
        nextpage = EnumNavigatie.SESSIE;
        SessieFrameController fc = new SessieFrameController(dc);
        Scene scene = new Scene(fc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxhbxActiesOnMouseClicked(MouseEvent event) {
        if (ac == null) {
            System.out.println("ActieController was null and is being initialized.");
            ac = new ActieController();
        }
        nextpage = EnumNavigatie.ACTIE;
        ActieFrameController fc = new ActieFrameController(dc);
        Scene scene = new Scene(fc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void hbxKlassenOnMouseClicked(MouseEvent event) {
        if (kc == null) {
            System.out.println("KlasController was null and is being initialized.");
            kc = new KlasController();
        }
        nextpage = EnumNavigatie.KLAS;
        KlasFrameController fc = new KlasFrameController(dc);
        Scene scene = new Scene(fc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }
}