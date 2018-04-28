package startup;

import domein.DomeinController;
import domein.PDF;
import gui.FrameHomeController;
import gui.HoofdMenuController;
import java.io.File;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartUpGuiClientApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        ///// folder aanmaken waar pdfs inkomen
        File pdfLocation = new File(PDF.FOLDERLOCATIE);
        if (!pdfLocation.exists()) {
            try {
                pdfLocation.mkdir();
            } catch (SecurityException se) {
                System.out.println(se.getMessage());
            }
        }
        Arrays.stream(pdfLocation.listFiles()).forEach(File::delete);


        FrameHomeController root = new FrameHomeController(new DomeinController());

        Scene scene = new Scene(root, 1430, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");

        primaryStage.getIcons().add(new Image("gui/img/favicon.png"));
        primaryStage.setTitle("Break Out Box");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        testconsole();
        launch(args);
    }

    private static void testconsole() {

    }
}
