package gui;

import domein.BoxController;
import domein.BoxObserver;
import domein.IBox;
import domein.Oefening;
import domein.UpdateItemTableObserver;
import domein.UpdateItemTableSubject;
import domein.Vak;
import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class BoxDetailPanelController extends VBox implements BoxObserver, UpdateItemTableSubject {
    
    private BoxController bc;
    private BoxFrameController fc;
    private Set<UpdateItemTableObserver> observers;
    
    @FXML private Label lblTitleRight;
    @FXML private Button btnNieuweOefening;
    @FXML private Button btnActies;
    @FXML private Button btnCreatePdf;
    @FXML private Label lblActiesCount;
    @FXML private ComboBox<Vak> ddlVak;
    @FXML private Button btnOefeningen;
    @FXML private Label lblOefeningenCount;
    @FXML private TextField txfOmschrijving;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Label lblError;
    @FXML private Label lblSuccess;
    @FXML private Button btnAddWithContent;
    @FXML private TextField txfNaam;
    @FXML private Label lblDoelstellingen;
    
    public BoxDetailPanelController(BoxController bcon, BoxFrameController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("BoxDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.bc = bcon;
        this.fc = fc; // parent controller for showing listview bewerkingen en doelstellingen
        this.observers = new HashSet<>();
        
        ddlVak.setItems(bc.geefVakken());
        initButtons(true);
    }

    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnAddWithContent.setVisible(!isNew);
        btnAddWithContent.setVisible(!isNew);
        btnCreatePdf.setVisible(!isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);
    }

    void initNieuweBox() {
        initButtons(true);
        clearRender();
    }
    
    private void clearRender() {
        bc.setListActiesVanBox(new ArrayList<>());
        bc.setListOefeningenVanBox(new ArrayList<>());

        initButtons(true);
        txfNaam.setText("");
        txfOmschrijving.setText("");
        ddlVak.setItems(bc.geefVakken());
        ddlVak.getSelectionModel().clearSelection();
        lblActiesCount.setText("0 acties geselecteerd");
        lblOefeningenCount.setText("0 oefeningen geselecteerd");
        lblDoelstellingen.setText("");
        lblError.setText("");
        lblSuccess.setText("");
        txfNaam.requestFocus();
    }

    @Override
    public void update(IBox o) {
        initButtons(false);
        txfNaam.setText(o.getNaam());
        txfOmschrijving.setText(o.getOmschrijving());
        ddlVak.setItems(bc.geefVakken());
        ddlVak.getSelectionModel().select(o.getVak());
        lblActiesCount.setText(o.getActies().size() + " acties geselecteerd");
        lblOefeningenCount.setText(o.getOefeningen().size() + " oefeningen geselecteerd");
        lblError.setText("");
        lblSuccess.setText("");
        updateDoelstellingen(o.getOefeningen());
    }
    
    private void updateDoelstellingen(List<Oefening> oef) {
        StringBuilder sb = new StringBuilder();
        oef.forEach(e -> e.getDoelstellingen().forEach(d -> {
            sb.append(d.getCode()).append(", ");
            if (sb.length() >= 72 && !sb.toString().substring(0, sb.length()-1).contains("\n"))
                sb.append("\n");
            if (sb.length() >= 144 && !sb.toString().substring(100, sb.length()-1).contains("\n"))
                sb.append("\n");
            if (sb.length() >= 216 && !sb.toString().substring(172, sb.length()-1).contains("\n"))
                sb.append("\n");
            if (sb.length() >= 288 && !sb.toString().substring(244, sb.length()-1).contains("\n"))
                sb.append("\n");
        }));
        
        // Delete the last ", "
        sb.setLength(sb.length() - 2);
        lblDoelstellingen.setText(sb.toString());
    }

    @Override
    public void updateCountActies() {
        lblActiesCount.setText(bc.getAantalTempActies() + " acties geselecteerd");
    }
    
    @Override
    public void updateCountOefeningen() {
        lblOefeningenCount.setText(bc.getAantalTempOefeningen() + " oefeningen geselecteerd");
        updateDoelstellingen(bc.getOefeningenListTemp());
    }
    
    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
        clearRender();
    }

    @FXML
    private void btnActiesOnAction(ActionEvent event) {
        fc.toonListview("act");
    }

    @FXML
    private void btnOefeningenOnAction(ActionEvent event) {
        fc.toonListview("oef");
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        try {
            bc.voegBoxToe(txfNaam.getText(), txfOmschrijving.getText(), ddlVak.getSelectionModel().getSelectedItem());
            
            initNieuweBox();
            lblError.setText("");
            lblSuccess.setText("De box werd succesvol toegevoegd.");
            fc.toonListview("cancel/init");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
        try {
            bc.pasBoxAan(txfNaam.getText(), txfOmschrijving.getText(), ddlVak.getSelectionModel().getSelectedItem());
            
            initNieuweBox();
            lblError.setText("");
            lblSuccess.setText("De box werd succesvol aangepast.");
            fc.toonListview("cancel/init");
            
            notifyUpdatedItem();
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnAddWithContentOnAction(ActionEvent event) {
        try {
            bc.voegBoxToe(txfNaam.getText(), txfOmschrijving.getText(), ddlVak.getSelectionModel().getSelectedItem());

            initNieuweBox();
            lblError.setText("");
            lblSuccess.setText("De aangepaste box werd succesvol toegevoegd.");
            fc.toonListview("cancel/init");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnCreatePdfOnAction(ActionEvent event) {
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("PDF met Box gegevens opslaan");
            chooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File selectedDirectory = chooser.showDialog((Stage) (this.getScene().getWindow()));

            bc.createPdf(selectedDirectory);

            /*sessie.createPDF(selectedDirectory);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Creatie");
            alert.setHeaderText("");
            alert.setContentText(String.format("%s: %s\\%s.pdf", "PDF aangemaakt in", selectedDirectory, sessie.getNaam()));
            alert.showAndWait();*/
        } catch (RuntimeException re) {
            System.out.println("fout");
        }
    }

    @Override
    public void addUpdatedItemObserver(UpdateItemTableObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeUpdatedItemObserver(UpdateItemTableObserver o) {
        observers.remove(o);
    }
    
    private void notifyUpdatedItem() {
        System.out.println(observers);
        observers.forEach((observer) -> {
            observer.updateEditedItem();
        });
    }
}