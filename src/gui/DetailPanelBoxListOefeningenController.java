/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Box;
import domein.BoxController;
import domein.BoxObserver;
import domein.BoxSubject;
import domein.IActie;
import domein.IBox;
import domein.IOefening;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class DetailPanelBoxListOefeningenController extends VBox implements BoxObserver, BoxSubject {
     
     
    FrameBoxController fc;
    BoxController bc;
    private List<IOefening> listOefeningenTempAlle = new ArrayList<>();
    private List<IOefening> listOefeningenTempGeselect = new ArrayList<>();
    private Set<BoxObserver> observers;
    @FXML
    private Label lblTitleLeftList;
    @FXML
    private Label lblAantalGeselecteerd;
    @FXML
    private ListView<IOefening> lsvListAlle;
    @FXML
    private ListView<IOefening> lsvListGeselecteerde;
    @FXML
    private Button btnDeselectAll;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;

    /**
     * Initializes the controller class.
     */
    public DetailPanelBoxListOefeningenController(BoxController bc, FrameBoxController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanelBoxListOefeningen.fxml"));
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
        lblTitleLeftList.setText("Oefeningen");
        lsvListAlle.setItems(bc.geefOefeningen());

        listOefeningenTempAlle = new ArrayList<>(bc.geefOefeningen());
        lsvListAlle.setItems(FXCollections.observableArrayList(listOefeningenTempAlle));

        lsvListAlle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IOefening>() { // moet veranderd woden
            @Override
            public void changed(ObservableValue<? extends IOefening> observable, IOefening oldValue, IOefening newValue) {
                if (!(newValue == null)) {
                    Platform.runLater(() -> {
                        listOefeningenTempAlle.remove(newValue);
                        lsvListAlle.setItems(FXCollections.observableArrayList(listOefeningenTempAlle));
                        listOefeningenTempGeselect.add(newValue);
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listOefeningenTempGeselect));
                        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listOefeningenTempGeselect.size());
                    });
                }
            }
        });

        lsvListGeselecteerde.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IOefening>() {
            @Override
            public void changed(ObservableValue<? extends IOefening> observable, IOefening oldValue, IOefening newValue) {
                if (!(newValue == null)) {

                    Platform.runLater(() -> {
                        listOefeningenTempGeselect.remove(newValue);
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listOefeningenTempGeselect));
                        listOefeningenTempAlle.add(newValue);
                        lsvListAlle.setItems(FXCollections.observableArrayList(listOefeningenTempAlle));
                        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listOefeningenTempGeselect.size());
                    });
                }
            }
        });

    }

    @Override
    public void update(IBox box) {
        lblAantalGeselecteerd.setText("Oefeningen geselecteerd: " + box.getOefeningen().size());
        List<? extends IOefening> lt = box.getOefeningen();
        listOefeningenTempGeselect = (List<IOefening>) lt;
        bc.setListOefeningenVanBoxTemp(listOefeningenTempGeselect);
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listOefeningenTempGeselect));

        listOefeningenTempAlle.removeAll(lt);
        lsvListAlle.setItems(FXCollections.observableArrayList(listOefeningenTempAlle));


        //List<IOefening
        //notifyObserversList();
    }

    public void nieuweOefening() {
        System.out.println("oefening wordt geinit");
        listOefeningenTempAlle = new ArrayList<>(bc.geefOefeningen());
        listOefeningenTempGeselect = new ArrayList<>();
        lsvListAlle.setItems(FXCollections.observableArrayList(listOefeningenTempAlle));
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listOefeningenTempGeselect));
        bc.setListOefeningenVanBoxTemp(listOefeningenTempGeselect);
        lblAantalGeselecteerd.setText("Geselecteerde oefeningen: 0");

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
        bc.setListOefeningenVanBoxTemp(listOefeningenTempGeselect);
        fc.toonListview("cancel/init");
        notifyObserversList();
    }


    @Override
    public void CountlistActiesVanBoxTemp() {

    }

    @Override
    public void CountlistOefeningenVanBoxTemp() {

    }

    @Override
    public void addBoxObserver(BoxObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeBoxObserver(BoxObserver o) {
        observers.remove(o);
    }
    private void notifyObserversList() {
        observers.forEach((observer) -> {
            observer.CountlistOefeningenVanBoxTemp();
        });
    }

}
