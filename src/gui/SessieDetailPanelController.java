package gui;

import domein.IBox;
import domein.IKlas;
import domein.ISessie;
import domein.SessieController;
import domein.SessieObserver;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SessieDetailPanelController extends VBox implements SessieObserver {

    private final SessieController sc;
    private final SessieFrameController fc;
    
    @FXML private Label lblTitleRight;
    @FXML private Button btnNieuweOefening;
    @FXML private TextField txfNaam;
    @FXML private Label lblAantalGroepen;
    @FXML private ComboBox<IBox> ddlBox;
    @FXML private ComboBox<IKlas> ddlKlas;
    @FXML private TextField txfOmschrijving;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnAddWithContent;
    @FXML private Label lblError;
    @FXML private Label lblSuccess;
    @FXML private Label lblCode;
    @FXML private DatePicker dtpStartDatum;
    
    public SessieDetailPanelController(SessieController sc, SessieFrameController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("SessieDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.sc = sc;
        this.fc = fc;
        initButtons(true);
    }

    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);
    }

    private void clearRender() {
        initButtons(true);
        lblCode.setText("");
        txfNaam.setText("");
        txfOmschrijving.setText("");
//        ddlBox.setItems(sc.geefBoxes());
        ddlBox.getSelectionModel().clearSelection();
//        ddlKlas.setItems(sc.geefKlassen());
        ddlKlas.getSelectionModel().clearSelection();
        lblAantalGroepen.setText("");
        dtpStartDatum.setValue(null);
        lblError.setText("");
        lblSuccess.setText("");
        txfNaam.requestFocus();
    }

    void initNieuw() {
        initButtons(true);
        clearRender();
    }

    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {
    }

    @FXML
    private void btnAddWithContentOnAction(ActionEvent event) {
    }

    @Override
    public void update(ISessie sessie) {
        
    }
    
}
