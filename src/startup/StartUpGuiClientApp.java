package startup;

import domein.DomeinControllerOefening;
import domein.PDF;
import gui.FrameHomeController;
import java.io.File;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StartUpGuiClientApp extends Application {
    
    private final String PERSISTENCE_UNIT_NAME = "breakoutboxPU";
    private EntityManager em;
    private EntityManagerFactory emf;
    
    @Override
    public void start(Stage primaryStage) {
        openPersistentie();

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

        FrameHomeController root = new FrameHomeController(new DomeinControllerOefening(), null, null);

        Scene scene = new Scene(root, 1280, 770, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("gui/css/style.css");

        primaryStage.getIcons().add(new Image("gui/img/favicon.png"));
        primaryStage.setTitle("Break Out Box Applicatie");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(
                e -> closePersistentie()
        );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie() {
        if (em != null) em.close();
        if (emf != null) emf.close();
        System.out.println("Entity Manager has been closed.");
    }
}