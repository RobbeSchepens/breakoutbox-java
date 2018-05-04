package gui;

import domein.ActieController;
import domein.ActieObserver;
import domein.IActie;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ActieDetailPanelController extends VBox implements ActieObserver {
    
    private final ActieController ac;
    private final ActieFrameController fc;

    @FXML private Label lblTitleRight;
    @FXML private Button btnNieuweOefening;
    @FXML private TextField txfNaam;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Label lblError;
    @FXML private Label lblSuccess;

    public ActieDetailPanelController(ActieController ac, ActieFrameController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("ActieDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.ac = ac;
        this.fc = fc;
        initButtons(true);
    }

    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);
    }
    
    @Override
    public void update(IActie actie) {
        clearRender();
        txfNaam.setText(actie.toString());
        initButtons(false);
    }
    
    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        try {
            ac.voegActieToe(txfNaam.getText());
            clearRender();
            lblError.setText("");
            lblSuccess.setText("De actie werd succesvol toegevoegd.");

        } catch (IllegalArgumentException ex) {
            lblSuccess.setText("");
            System.out.println(ex.getMessage());
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
        try {
            ac.pasActieAan(txfNaam.getText());
            //clearRender();
            lblError.setText("");
            lblSuccess.setText("De actie werd succesvol aangepast.");
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
        txfNaam.setText("");
    }
}