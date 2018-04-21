package startup;

import domein.DomeinController;
import gui.HoofdMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartUpGuiClientApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        HoofdMenuController root = new HoofdMenuController(new DomeinController());

        Scene scene = new Scene(root, 1280, 720, Color.web("#ffffff"));
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
