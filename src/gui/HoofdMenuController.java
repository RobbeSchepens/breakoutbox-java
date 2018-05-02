package gui;

import domein.ActieController;
import domein.BoxController;
import domein.KlasController;
import domein.OefeningController;
import domein.SessieController;
import static gui.MenubarController.nextpage;
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
    
    private OefeningController dc;
    private BoxController bc;
    private KlasController kc;
    private ActieController ac;
    private SessieController scc;
    
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
    
    public HoofdMenuController(OefeningController dcon, KlasController kc, BoxController bc, SessieController sc, ActieController ac) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HoofdMenu.fxml"));
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
        this.scc = sc;
        this.ac = ac;
        
        laadAantallen();
        setMouseHoverIconButtons();
    }

    private void laadAantallen() {
        lblAantalOef.setText(String.valueOf(dc.geefAantalOefeningen()));
        lblAantalBoxes.setText(String.valueOf(bc.geefAantalBoxen()));
        //lblAantalSessies.setText(String.valueOf(scc.geefAantalSessies()));
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
        if (dc == null) {
            System.out.println("OefeningController was null and is being initialized.");
            dc = new OefeningController();
        }
        nextpage = EnumMenu.OEFENING;
        FrameOefeningController sc = new FrameOefeningController(dc, kc, bc, scc, ac);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
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
        nextpage = EnumMenu.BOX;
        FrameBoxController sc = new FrameBoxController(dc, kc, bc, scc, ac);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void btnBeheerSessiesOnAction(ActionEvent event) {
        if (scc == null) {
            System.out.println("SessieController was null and is being initialized.");
            scc = new SessieController();
        }
        nextpage = EnumMenu.SESSIE;
        FrameSessieController sc = new FrameSessieController(dc, kc, bc, scc, ac);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void btnBeheerActiesOnAction(ActionEvent event) {
        if (ac == null) {
            System.out.println("ActieController was null and is being initialized.");
            ac = new ActieController();
        }
        nextpage = EnumMenu.ACTIE;
        FrameActieController sc = new FrameActieController(dc, kc, bc, scc, ac);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

    @FXML
    private void btnBeheerKlassenOnAction(ActionEvent event) {
        if (kc == null) {
            System.out.println("KlasController was null and is being initialized.");
            kc = new KlasController();
        }
        nextpage = EnumMenu.KLAS;
        FrameKlassenController sc = new FrameKlassenController(dc, kc, bc, scc, ac);
        Scene scene = new Scene(sc, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");
        ((Stage) this.getScene().getWindow()).setScene(scene);
    }

}
