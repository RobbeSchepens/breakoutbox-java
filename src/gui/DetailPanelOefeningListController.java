package gui;

import domein.DomeinController;
import domein.Groepsbewerking;
import domein.IOefening;
import domein.OefeningObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DetailPanelOefeningListController extends VBox implements OefeningObserver {

    private DomeinController dc;
    private FrameOefeningController fc;
    private List<Groepsbewerking> listGroepsBewerkingenTempAlle = new ArrayList<>();
    private List<Groepsbewerking> listGroepsBewerkingenTempGeselect = new ArrayList<>();


    @FXML private Label lblTitleLeftList;
    @FXML private Label lblAantalGeselecteerd;
    @FXML private Button btnDeselectAll;
    @FXML private Button btnCancel;
    @FXML private Button btnSubmit;
    @FXML private ListView<Groepsbewerking> lsvListAlle;
    @FXML private ListView<Groepsbewerking> lsvListGeselecteerde;

    public DetailPanelOefeningListController(DomeinController dcon, FrameOefeningController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanelOefeningList.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dcon;
        this.fc = fc;

        List<Groepsbewerking> p = dc.getGroepsbewerkingenList();
        for (Groepsbewerking item : p) {
                listGroepsBewerkingenTempAlle.add(item);
        }


        lsvListAlle.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempAlle));
        lblTitleLeftList.setText("Groepsbewerkingen");

        lsvListAlle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Groepsbewerking>() {
            @Override
            public void changed(ObservableValue<? extends Groepsbewerking> observable, Groepsbewerking oldValue, Groepsbewerking newValue) {

                if (!(newValue == null)) {
                    listGroepsBewerkingenTempAlle.remove(newValue);
                    Platform.runLater(() -> {
                        lsvListAlle.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempAlle));
                        listGroepsBewerkingenTempGeselect.add(newValue);
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempGeselect));
                        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listGroepsBewerkingenTempGeselect.size());
                    });
                }
            }
        });

        lsvListGeselecteerde.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Groepsbewerking>() {
            @Override
            public void changed(ObservableValue<? extends Groepsbewerking> observable, Groepsbewerking oldValue, Groepsbewerking newValue) {

                if (!(newValue == null)) {
                    listGroepsBewerkingenTempGeselect.remove(newValue);
                    Platform.runLater(() -> {
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempGeselect));
                        listGroepsBewerkingenTempAlle.add(newValue);
                        lsvListAlle.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempAlle));
                        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listGroepsBewerkingenTempGeselect.size());
                    });
                }
            }
        });
    }


    @Override
    public void update(IOefening oefening) {

        listGroepsBewerkingenTempAlle.clear();
        List<Groepsbewerking> p = dc.getGroepsbewerkingenList();
        for (Groepsbewerking item : p) {
            listGroepsBewerkingenTempAlle.add(item);
        }

        List<Groepsbewerking> m = dc.geefGroepsbewerkingenHuidigeOefening();
        listGroepsBewerkingenTempGeselect = new ArrayList<>();
        for (Groepsbewerking item : m) {
            listGroepsBewerkingenTempGeselect.add(item);
        }
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempGeselect));

        //listGroepsBewerkingenTempAlle.removeAll(listGroepsBewerkingenTempGeselect); //werkt niet deftig xd
        ArrayList<Groepsbewerking> h = new ArrayList<>();
        for (int j = 0; j < listGroepsBewerkingenTempGeselect.size(); j++) {
            for (int i = 0; i < listGroepsBewerkingenTempAlle.size(); i++) {
                if (listGroepsBewerkingenTempAlle.get(i).toString().equals(listGroepsBewerkingenTempGeselect.get(j).toString())) {
                    h.add(listGroepsBewerkingenTempAlle.get(i));
                }
            }
        }
        listGroepsBewerkingenTempAlle.removeAll(h);

        listGroepsBewerkingenTempAlle.removeAll(listGroepsBewerkingenTempGeselect);
        lsvListAlle.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempAlle));
        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listGroepsBewerkingenTempGeselect.size());
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
        dc.setListGroepsbewerkingenVanOefening(listGroepsBewerkingenTempGeselect); // de temp list
        fc.toonListview("cancel/init");
        //Dit crasht bij nieuwe oefening, er bestaat nog geen huidige oefening
        //dc.setGroepsbewerkingenOefening(lsvListGeselecteerde.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {
        
    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
        
    }

    void nieuweOefening() {
        listGroepsBewerkingenTempGeselect = new ArrayList<>();
        listGroepsBewerkingenTempAlle = dc.getGroepsbewerkingenList();
        dc.setListGroepsbewerkingenVanOefening(listGroepsBewerkingenTempGeselect);
        lsvListAlle.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempAlle));
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempGeselect));
        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listGroepsBewerkingenTempGeselect.size());

    }

}
