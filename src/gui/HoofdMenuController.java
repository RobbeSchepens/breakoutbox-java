package gui;

import domein.ActieController;
import domein.BoxController;
import domein.DomeinController;
import domein.KlasController;
import domein.OefeningController;
import domein.SessieController;
import static gui.NavigatieController.nextpage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HoofdMenuController extends GridPane {
    
    private DomeinController dc;
    private OefeningController oc;
    private BoxController bc;
    private SessieController sc;
    private KlasController kc;
    private ActieController ac;
    
    @FXML private Button btnBeheerBob;
    @FXML private Button btnBeheerSessies;
    @FXML private Button btnBeheerOef;
    @FXML private Label lblAantalOef;
    @FXML private Label lblAantalBoxes;
    @FXML private Label lblAantalSessies;
    @FXML private Button btnBeheerActies;
    @FXML private Label lblAantalActies;
    @FXML private Button btnBeheerKlassen;
    @FXML private Label lblAantalKlassen;
    
    public HoofdMenuController(DomeinController dc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HoofdMenu.fxml"));
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
        
        laadAantallen();
        setMouseHoverIconButtons();
    }

    private void laadAantallen() {
        lblAantalOef.setText(String.valueOf(oc.geefAantalOefeningen()));
        lblAantalBoxes.setText(String.valueOf(bc.geefAantalBoxen()));
        //lblAantalSessies.setText(String.valueOf(sc.geefAantalSessies()));
        lblAantalActies.setText(String.valueOf(ac.geefAantalActies()));
        lblAantalKlassen.setText(String.valueOf(kc.geefAantalKlassen()));
    }
    
    private void setMouseHoverIconButtons() {
        ((VBox)this.getChildren().get(0)).getChildren().forEach(e -> {
            if (e instanceof Button) {
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
    private void btnBeheerOefOnAction(ActionEvent event) {
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
    private void btnBeheerBobOnAction(ActionEvent event) {
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
    private void btnBeheerSessiesOnAction(ActionEvent event) {
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
    private void btnBeheerActiesOnAction(ActionEvent event) {
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
    private void btnBeheerKlassenOnAction(ActionEvent event) {
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
