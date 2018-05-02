package gui;

import domein.IKlas;
import domein.KlasController;
import domein.KlasObserver;
import domein.Leerling;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KlasDetailPanelController extends VBox implements KlasObserver {

    KlasController kc;
    KlasFrameController fc;
    private FileChooser fileChooserExcel;

    @FXML private Label lblTitleRight;
    @FXML private Button btnNieuweOefening;
    @FXML private TextField txfNaamKlas;
    @FXML private TextField txfNaamLln;
    @FXML private Label lblOpgave1;
    @FXML private Label lblUploadExcel;
    @FXML private TextField txfVoornaam;
    @FXML private ListView<Leerling> lsvLeerlingen;
    @FXML private Label lblOpgave;
    @FXML private Button btnFileOpgave;
    @FXML private Label lblExcelName;
    @FXML private Label lblError;
    @FXML private Label lblSuccess;
    @FXML private Button btnEdit;
    @FXML private Button btnAdd;
    @FXML private Label lblGeselect;
    @FXML private Label lblToegevoegdBoodschap;

    public KlasDetailPanelController(KlasController kc, KlasFrameController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("KlasDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.kc = kc;
        this.fc = fc;
        initButtons(true);

        lsvLeerlingen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Leerling>() {
            @Override
            public void changed(ObservableValue<? extends Leerling> observable, Leerling oldValue, Leerling newValue) {
                if (!(newValue == null)) {
                    lblGeselect.setText("Geselecteerd: " + newValue.toString());
                }
            }
        });
    }

    @Override
    public void update(IKlas klas) {
        clearRender();
        initButtons(false);

        txfNaamKlas.setText(klas.getNaam());
        lsvLeerlingen.setItems(FXCollections.observableArrayList(klas.getLeerlingen()));
        lblTitleRight.setText("Klas");
        lblUploadExcel.setVisible(false);
        btnFileOpgave.setVisible(false);
    }

    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);
    }
    
    @FXML
    private void btnVoegLlnToeOnAction(ActionEvent event) {
        int i = 0;
        boolean test = false;
        Leerling ln = new Leerling(txfNaamLln.getText(), txfVoornaam.getText());
        txfNaamLln.setText("");
        txfVoornaam.setText("");
        for (Leerling item : lsvLeerlingen.getItems()) {
            if (item.getVoornaam().trim().equals(ln.getVoornaam().trim()) && item.getAchternaam().trim().equals(ln.getAchternaam().trim())) {
                lblToegevoegdBoodschap.setText("Leerling bestaat al.");
                test = true;
            }
        }
        if (test) {
            lblToegevoegdBoodschap.setText("Leerling al in lijst!");
        } else {
            lsvLeerlingen.getItems().add(ln);
            lblToegevoegdBoodschap.setText(ln.getVoornaam() + " toegevoegd!");
        }
    }
    
    @FXML
    private void btnVerwijderLlnOnAction(ActionEvent event) {
        Leerling g = lsvLeerlingen.getSelectionModel().getSelectedItem();
        lsvLeerlingen.getItems().remove(g);
        lblGeselect.setText(g.getVoornaam() + " verwijderd!");
        lsvLeerlingen.getSelectionModel().clearSelection();
    }

    @FXML
    private void btnUploadExcelOnAction(ActionEvent event) throws IOException {
        FileChooser excelChooser = new FileChooser();
        excelChooser.setTitle("Kies een klas");
        excelChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        excelChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xlsx", "*.xlsx"));
        File excelFile = excelChooser.showOpenDialog((Stage) (this.getScene().getWindow()));

        try (FileInputStream inputStream = new FileInputStream(excelFile); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                String voornaam = nextRow.getCell(0).getStringCellValue().trim();
                String achternaam = nextRow.getCell(1).getStringCellValue().trim();
                if (!voornaam.isEmpty()) {
                    if (!voornaam.matches("^[a-zA-Z\\s]*$")) {
                    } else {
                        lsvLeerlingen.getItems().add(new Leerling(voornaam, achternaam));
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("io exception in excel uploaden button");
        } catch (NullPointerException npe) {
            System.out.println("nullpointer exception in excel uploaden button");
        } catch (Exception e) {
            System.out.println("exception exception in excel uploaden button");
        }


    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        try {
            kc.voegKlasToe(txfNaamKlas.getText(), lsvLeerlingen.getItems());
            clearRender();
            lblError.setText("");
            lblSuccess.setText("De klas werd succesvol toegevoegd.");
        } catch (IllegalArgumentException ex) {
            lblSuccess.setText("");
            System.out.println(ex.getMessage());
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
        try {
            kc.pasOefeningAan(txfNaamKlas.getText(), lsvLeerlingen.getItems());
            clearRender();
            lblError.setText("");
            lblSuccess.setText("De klas werd succesvol aangepast.");
            fc.notifyChangeVoorAantalLln();
        } catch (IllegalArgumentException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }

    void initNieuweOefening() {
        initButtons(true);
        clearRender();
    }

    private void clearRender() {
        initButtons(true);
        lsvLeerlingen.getItems().clear();
        txfNaamKlas.setText("");
        txfNaamLln.setText("");
        txfVoornaam.setText("");
        lblGeselect.setText("");
        lblToegevoegdBoodschap.setText("");
        btnFileOpgave.setVisible(true);
        lblUploadExcel.setVisible(true);
    }
}