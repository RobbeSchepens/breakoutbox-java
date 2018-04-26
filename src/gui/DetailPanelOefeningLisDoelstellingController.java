/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Doelstelling;
import domein.DomeinController;

import domein.IOefening;
import domein.OefeningObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class DetailPanelOefeningLisDoelstellingController extends VBox implements OefeningObserver {

    private DomeinController dc;
    private FrameOefeningController fc;
    private List<Doelstelling> listDoelstellingenTempAlle = new ArrayList<>();
    private List<Doelstelling> listDoelstellingenTempGeselect = new ArrayList<>();

    @FXML
    private Label lblTitleLeftList;
    @FXML
    private Label lblAantalGeselecteerd;
    @FXML
    private Button btnDeselectAll;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private ListView<Doelstelling> lsvListAlle;
    @FXML
    private ListView<Doelstelling> lsvListGeselecteerde;

    public DetailPanelOefeningLisDoelstellingController(DomeinController dcon, FrameOefeningController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanelOefeningLisDoelstelling.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.dc = dcon;
        this.fc = fc;

        List<Doelstelling> p = dc.getDoelstellingenList();
        for (Doelstelling item : p) {
            listDoelstellingenTempAlle.add(item);
        }

        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
        lblTitleLeftList.setText("Groepsbewerkingen");

        lsvListAlle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doelstelling>() {
            @Override
            public void changed(ObservableValue<? extends Doelstelling> observable, Doelstelling oldValue, Doelstelling newValue) {
                if (!(newValue == null)) {
                    listDoelstellingenTempAlle.remove(newValue);
                    Platform.runLater(() -> {
                        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
                        listDoelstellingenTempGeselect.add(newValue);
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
                        lblAantalGeselecteerd.setText("Doelstellingen geselecteerd: " + listDoelstellingenTempGeselect.size());
                    });
                }
            }
        });

        lsvListGeselecteerde.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doelstelling>() {
            @Override
            public void changed(ObservableValue<? extends Doelstelling> observable, Doelstelling oldValue, Doelstelling newValue) {
                if (!(newValue == null)) {
                    listDoelstellingenTempGeselect.remove(newValue);
                    Platform.runLater(() -> {
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
                        listDoelstellingenTempAlle.add(newValue);
                        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
                        lblAantalGeselecteerd.setText("Doelstellingen geselecteerd: " + listDoelstellingenTempGeselect.size());
                    });
                }
            }
        });
    }
    @Override
    public void update(IOefening oefening) {


        List<Doelstelling> m = dc.geefDoelstellingenHuidigeOefening();
        listDoelstellingenTempGeselect = new ArrayList<>();
        for (Doelstelling item : m) {
            listDoelstellingenTempGeselect.add(item);
        }
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
        listDoelstellingenTempAlle.removeAll(listDoelstellingenTempGeselect);
        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
        System.out.println("UpdateDoels");
        System.out.println("alle " + listDoelstellingenTempAlle);
        System.out.println("select " + listDoelstellingenTempGeselect);
    }
    @FXML
    private void btnDeselectAllOnAction(ActionEvent event) {
        lsvListGeselecteerde.getSelectionModel().clearSelection();

    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        fc.toonListview("cancel/init");
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
        dc.setListDoelstellingenVanOefening(listDoelstellingenTempGeselect);

        //Dit crasht bij nieuwe oefening, er bestaat nog geen huidige oefening
        //dc.setDoelstellingenOefening(lsvListGeselecteerde.getSelectionModel().getSelectedItems());
    }
    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {

    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
    }





}
