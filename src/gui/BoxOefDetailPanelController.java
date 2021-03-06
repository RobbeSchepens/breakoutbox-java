package gui;

import domein.BoxController;
import domein.BoxObserver;
import domein.BoxSubject;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class BoxOefDetailPanelController extends VBox implements BoxObserver, BoxSubject {
     
    private final BoxFrameController fc;
    private final BoxController bc;
    private List<IOefening> listOefeningenTempAlle = new ArrayList<>();
    private List<IOefening> listOefeningenTempGeselect = new ArrayList<>();
    private final Set<BoxObserver> observers;
    
    @FXML private Label lblTitleLeftList;
    @FXML private Label lblAantalGeselecteerd;
    @FXML private ListView<IOefening> lsvListAlle;
    @FXML private ListView<IOefening> lsvListGeselecteerde;
    @FXML private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private Label lblAantalBeschikbaar;

    public BoxOefDetailPanelController(BoxController bc, BoxFrameController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BoxOefDetailPanel.fxml"));
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
                        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listOefeningenTempGeselect.size());
                        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listOefeningenTempAlle.size());
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
                        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listOefeningenTempGeselect.size());
                        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listOefeningenTempAlle.size());
                    });
                }
            }
        });
    }

    @Override
    public void update(IBox box) {
        listOefeningenTempAlle = new ArrayList<>(bc.geefOefeningen());
        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + box.getOefeningen().size());


        //List<? extends IOefening> lt = box.getOefeningen();
        //listOefeningenTempGeselect = (List<IOefening>) lt;

        List<IOefening> m = new ArrayList<>(bc.geefOefeningenHuidigeBox());
        listOefeningenTempGeselect = new ArrayList<>();
        m.forEach(item -> listOefeningenTempGeselect.add(item));

        bc.setListOefeningenVanBox(listOefeningenTempGeselect);
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listOefeningenTempGeselect));

        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listOefeningenTempGeselect.size());
        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listOefeningenTempAlle.size());
        ArrayList<IOefening> h = new ArrayList<>();
        for (int j = 0; j < listOefeningenTempGeselect.size(); j++) {
            for (int i = 0; i < listOefeningenTempAlle.size(); i++) {
                if (listOefeningenTempAlle.get(i).getNaam().equals(listOefeningenTempGeselect.get(j).getNaam())) {
                    h.add(listOefeningenTempAlle.get(i));
                }
            }
        }
        listOefeningenTempAlle.removeAll(h);

        lsvListAlle.setItems(FXCollections.observableArrayList(listOefeningenTempAlle));
        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listOefeningenTempAlle.size());
    }

    public void nieuweBox() {
        listOefeningenTempAlle = new ArrayList<>(bc.geefOefeningen());
        listOefeningenTempGeselect = new ArrayList<>();
        lsvListAlle.setItems(FXCollections.observableArrayList(listOefeningenTempAlle));
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listOefeningenTempGeselect));
        bc.setListOefeningenVanBox(listOefeningenTempGeselect);
        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listOefeningenTempGeselect.size());
        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listOefeningenTempAlle.size());
    }

    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
    }


    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        fc.toonListview("cancel/init");
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
        bc.setListOefeningenVanBox(listOefeningenTempGeselect);
        fc.toonListview("cancel/init");
        notifyObserversList();
    }

    @Override
    public void addBoxObserver(BoxObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeBoxObserver(BoxObserver o) {
        observers.remove(o);
    }
    
    private void notifyObserversList() {
        observers.forEach((observer) -> {
            observer.updateCountOefeningen();
        });
    }

    @Override
    public void updateCountActies() {}

    @Override
    public void updateCountOefeningen() {}
}