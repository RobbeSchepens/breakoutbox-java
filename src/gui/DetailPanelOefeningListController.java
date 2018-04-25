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

                    });
                }
            }
        });

    }   

    @FXML
    private void btnDeselectAllOnAction(ActionEvent event) {
        lsvListGeselecteerde.getSelectionModel().clearSelection();
    }

    @Override
    public void update(IOefening oefening) {
        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " 
                + dc.geefGroepsbewerkingenHuidigeOefening().size());

        List<Groepsbewerking> m = dc.geefGroepsbewerkingenHuidigeOefening();
        for (Groepsbewerking item : m) {
            listGroepsBewerkingenTempGeselect.add(item);
        }

        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempGeselect));
        listGroepsBewerkingenTempAlle.removeAll(listGroepsBewerkingenTempGeselect);
        lsvListAlle.setItems(FXCollections.observableArrayList(listGroepsBewerkingenTempAlle));

    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        fc.toonListview(false);
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
        dc.setGroepsbewerkingenOefening(lsvListGeselecteerde.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {
        
    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
        
    }
}