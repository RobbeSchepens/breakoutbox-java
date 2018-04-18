package startup;

import domein.Controller;
import domein.KlasController;
import domein.SessieController;
import gui.HoofdMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
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
        testconsole();
        launch(args);
    }

    private static void testconsole(){
        Controller controller = new Controller();
        SessieController sessieController = new SessieController();
        System.out.println("Sessies:");
        System.out.println(sessieController.geefSessieLijst());
//        KlasController klasController = new KlasController();
//        System.out.println("\nKlas met leerlingen:");
//        System.out.println(klasController.geefLeerlingenVanKlasLijst(0));
        controller.close();
    }
}
