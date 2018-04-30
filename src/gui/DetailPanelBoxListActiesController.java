/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Actie;
import domein.BoxController;
import domein.BoxObserver;
import domein.Groepsbewerking;
import domein.IActie;
import domein.IBox;
import domein.IKlas;
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
public class DetailPanelBoxListActiesController extends VBox implements BoxObserver {

    FrameBoxController fc;
    BoxController bc;
    private List<IActie> listActiesTempAlle;
    private List<IActie> listActiesTempGeselect = new ArrayList<>();

    @FXML
    private Label lblTitleLeftList;
    @FXML
    private Label lblAantalGeselecteerd;
    @FXML
    private ListView<IActie> lsvListAlle;
    @FXML
    private ListView<IActie> lsvListGeselecteerde;
    @FXML
    private Button btnDeselectAll;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;

    /**
     * Initializes the controller class.
     */
    public DetailPanelBoxListActiesController(BoxController bc, FrameBoxController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanelBoxListActies.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.bc = bc;
        this.fc = fc;
        lblTitleLeftList.setText("Acties");
        listActiesTempAlle = new ArrayList<>(bc.geefActies());
        lsvListAlle.setItems(FXCollections.observableArrayList(listActiesTempAlle));

        lsvListAlle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IActie>() { // moet veranderd woden
            @Override
            public void changed(ObservableValue<? extends IActie> observable, IActie oldValue, IActie newValue) {
                if (!(newValue == null)) {
                    Platform.runLater(() -> {
                        listActiesTempAlle.remove(newValue);
                        lsvListAlle.setItems(FXCollections.observableArrayList(listActiesTempAlle));
                        listActiesTempGeselect.add(newValue);
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listActiesTempGeselect));
                        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listActiesTempGeselect.size());
                    });
                }
            }
        });

        lsvListGeselecteerde.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IActie>() {
            @Override
            public void changed(ObservableValue<? extends IActie> observable, IActie oldValue, IActie newValue) {
                if (!(newValue == null)) {

                    Platform.runLater(() -> {
                        listActiesTempGeselect.remove(newValue);
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listActiesTempGeselect));
                        listActiesTempAlle.add(newValue);
                        lsvListAlle.setItems(FXCollections.observableArrayList(listActiesTempAlle));
                        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listActiesTempGeselect.size());
                    });
                }
            }
        });
    }

    @Override
    public void update(IBox box) {
        System.out.println("update");
    }

    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void btnDeselectAllOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        fc.toonListview("cancel/init");
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
        //hier nog dingen
        fc.toonListview("cancel/init");
    }


}
