package gui;

import domein.IBox;
import domein.IKlas;
import domein.ISessie;
import domein.SessieController;
import domein.SessieObserver;
import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class SessieDetailPanelController extends VBox implements SessieObserver {

    private final SessieController sc;
    private final SessieFrameController fc;
    final ToggleGroup grpRadioButtons = new ToggleGroup();
    
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
    @FXML private Slider sliGroepen;
    @FXML private CheckBox cbxAfstandsonderwijs;
    @FXML private RadioButton radGroepenAuto;
    @FXML private RadioButton radGroepenHandLeerkracht;
    @FXML private RadioButton radGroepenHandLeerlingen;
    
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
        ddlKlas.setItems(sc.geefKlassen());
        ddlBox.setItems(sc.geefBoxes());
        radGroepenAuto.setToggleGroup(grpRadioButtons);
        radGroepenHandLeerkracht.setToggleGroup(grpRadioButtons);
        radGroepenHandLeerlingen.setToggleGroup(grpRadioButtons);
        radGroepenAuto.setSelected(true);
        
        sliGroepen.valueProperty().addListener((obs, oldval, newVal) -> 
                sliGroepen.setValue(newVal.intValue()));
        
        sliGroepen.valueProperty().addListener((ObservableValue<? extends Number> observable, 
                Number oldValue, Number newValue) -> {
            lblAantalGroepen.setText(String.valueOf(newValue.intValue()));
        });
        
        ddlKlas.valueProperty().addListener((ObservableValue<? extends IKlas> observable, 
                IKlas oldValue, IKlas newValue) -> {
            sliGroepen.setMax(Math.ceil((double)newValue.getLeerlingen().size() / 4));
        });
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
        ddlBox.setItems(sc.geefBoxes());
        ddlBox.getSelectionModel().clearSelection();
        ddlKlas.setItems(sc.geefKlassen());
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
        try {
            String typegroep = "";
            if (radGroepenAuto.isSelected())
                typegroep = "auto";
            if (radGroepenHandLeerkracht.isSelected())
                typegroep = "handleerkracht";
            if (radGroepenHandLeerlingen.isSelected())
                typegroep = "handleerling";
            
            sc.voegNieuweSessieToe(txfNaam.getText(), txfOmschrijving.getText(), 
                    ddlKlas.getSelectionModel().getSelectedItem(),
                    ddlBox.getSelectionModel().getSelectedItem(),
                    cbxAfstandsonderwijs.isSelected(), typegroep, 
                    (int)sliGroepen.getValue(), dtpStartDatum.getValue());
            
            initNieuw();
            lblError.setText("");
            lblSuccess.setText("De sessie werd succesvol toegevoegd.");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
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