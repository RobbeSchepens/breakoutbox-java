package startup;

import domein.SessieController;
import gui.HoofdMenuController;
import static java.lang.System.exit;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartUpGuiClientApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        HoofdMenuController root = new HoofdMenuController();

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Break Out Box");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); //GUI

        /*SessieController sessieController = new SessieController();
        System.out.println("Sessies :");
        System.out.println(sessieController.geefSessieLijst());
        sessieController.close();
        exit(0);*/
    }

}
