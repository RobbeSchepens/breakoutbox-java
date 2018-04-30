/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.BoxController;
import domein.BoxObserver;
import domein.IBox;
import domein.Vak;
import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelBoxController extends VBox implements BoxObserver {
    private BoxController bc;
    private FrameBoxController fc;
    private Set<BoxObserver> observers;
    @FXML
    private Label lblTitleRight;
    @FXML
    private Button btnNieuweOefening;
    @FXML
    private Button btnOefeningen;
    @FXML
    private Label lblOefeningenCount;
    @FXML
    private Label lblFeedback;
    @FXML
    private ComboBox<Vak> ddlVak;
    @FXML
    private Button btnActies;
    @FXML
    private Label lblActiesCount;
    @FXML
    private TextField txfOmschrijving;
    @FXML
    private TextField tfxNaam;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Label lblError;
    @FXML
    private Label lblSuccess;

    @FXML
    private Button btnAddWithContent;

    public DetailPanelBoxController(BoxController bc, FrameBoxController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("DetailPanelBox.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.bc = bc;
        this.fc = fc;
        this.observers = new HashSet<>();
        ddlVak.setItems(bc.geefVakken());
        initButtons(true);
    }
    private void initButtons(boolean isNew) {
        btnAdd.setManaged(isNew);
        btnAdd.setVisible(isNew);

        btnAddWithContent.setManaged(!isNew);
        btnAddWithContent.setVisible(!isNew);
        btnEdit.setManaged(!isNew);
        btnEdit.setVisible(!isNew);

    }

    @Override
    public void update(IBox box) {
        clearRender();
        initButtons(false);
        lblTitleRight.setText("Overzicht Box");
        tfxNaam.setText(box.getNaam());
        txfOmschrijving.setText(box.getOmschrijving());
        ddlVak.setItems(bc.geefVakken());
        ddlVak.getSelectionModel().select(box.getVak());
        lblActiesCount.setText(bc.CountlistOefeningenVanBoxTemp() + " acties geselecteerd");
        lblOefeningenCount.setText(bc.CountlistOefeningenVanBoxTemp() + " oefeningen geselecteerd");
        lblError.setText("");
        lblSuccess.setText("");
    }

    @FXML
    private void btnNieuweOefeningOnAction(ActionEvent event) {
    }

    @FXML
    private void btnOefeningenOnAction(ActionEvent event) {
        fc.toonListview("oef");
    }

    @FXML
    private void btnActiesonAction(ActionEvent event) {
        fc.toonListview("act");
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        try {
            bc.voegBoxToe(tfxNaam.getText(), txfOmschrijving.getText(), ddlVak.getSelectionModel().getSelectedItem());
            clearRender();
            fc.toonListview("cancel/init");
            fc.initNieuweOefening();
            lblSuccess.setText("De box werd succesvol toegevoegd");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }
    }

    @FXML
    private void btnEditOnAction(ActionEvent event) {

        fc.toonListview("cancel/init");
    }

    @FXML
    private void btnAddWithContentOnAction(ActionEvent event) {
        try {
            bc.voegBoxToe(tfxNaam.getText(), txfOmschrijving.getText(), ddlVak.getSelectionModel().getSelectedItem());
            clearRender();
            fc.toonListview("cancel/init");
            lblSuccess.setText("De box werd succesvol toegevoegd");
        } catch (SpecialeTekensInNaamException | IllegalArgumentException | NaamTeKortException | NaamTeLangException ex) {
            lblSuccess.setText("");
            lblError.setText(ex.getMessage());
        }

    }


    private void clearRender() {
        initButtons(true);
        tfxNaam.setText("");
        txfOmschrijving.setText("");
        ddlVak.setItems(bc.geefVakken());
        ddlVak.getSelectionModel().clearSelection();
        lblOefeningenCount.setText("0 oefeningen geselecteerd");
        lblActiesCount.setText("0 acties geselecteerd");


    }
    void initNieuweOefening() {
        initButtons(true);
        clearRender();
    }

    @Override
    public void CountlistActiesVanBoxTemp() {

        System.out.println(bc.CountlistActiesVanBoxTemp());
        lblActiesCount.setText(bc.CountlistActiesVanBoxTemp() + " acties geselecteerd");
    }

    @Override
    public void CountlistOefeningenVanBoxTemp() {
        System.out.println(bc.CountlistActiesVanBoxTemp());
        lblOefeningenCount.setText(bc.CountlistOefeningenVanBoxTemp() + " oefeningen geselecteerd");
    }


}
