package startup;

import domein.DomeinController;
import domein.PDF;
import gui.HoofdMenuController;
import gui.OefeningFrameController;
import java.io.File;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
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
        /////////

        HoofdMenuController root = new HoofdMenuController(new DomeinController());

        Scene scene = new Scene(root, 1280, 720);
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
