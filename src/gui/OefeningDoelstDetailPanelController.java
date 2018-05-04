package gui;

import domein.Doelstelling;
import domein.OefeningController;
import domein.IOefening;
import domein.OefeningObserver;
import domein.OefeningSubject;
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

public class OefeningDoelstDetailPanelController extends VBox implements OefeningObserver, OefeningSubject {

    private final OefeningController dc;
    private final OefeningFrameController fc;
    private List<Doelstelling> listDoelstellingenTempAlle = new ArrayList<>();
    private List<Doelstelling> listDoelstellingenTempGeselect = new ArrayList<>();
    private final Set<OefeningObserver> observers;

    @FXML private Label lblTitleLeftList;
    @FXML private Label lblAantalGeselecteerd;
    @FXML private Button btnCancel;
    @FXML private Button btnSubmit;
    @FXML private ListView<Doelstelling> lsvListAlle;
    @FXML
    private ListView<Doelstelling> lsvListGeselecteerde;
    @FXML
    private Label lblAantalBeschikbaar;

    public OefeningDoelstDetailPanelController(OefeningController dcon, OefeningFrameController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OefeningDoelstDetailPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.observers = new HashSet<>();
        this.dc = dcon;
        this.fc = fc;

        List<Doelstelling> p = new ArrayList<>(dc.geefDoelstellingen());
        for (Doelstelling item : p) {
            listDoelstellingenTempAlle.add(item);
        }

        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listDoelstellingenTempGeselect.size());
        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listDoelstellingenTempAlle.size());
        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
        lblTitleLeftList.setText("Doelstellingen");
        lsvListAlle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doelstelling>() {
            @Override
            public void changed(ObservableValue<? extends Doelstelling> observable, Doelstelling oldValue, Doelstelling newValue) {
                if (!(newValue == null)) {
                    listDoelstellingenTempAlle.remove(newValue);
                    Platform.runLater(() -> {
                        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
                        listDoelstellingenTempGeselect.add(newValue);
                        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
                        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listDoelstellingenTempGeselect.size());
                        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listDoelstellingenTempAlle.size());
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
                        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listDoelstellingenTempGeselect.size());
                        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listDoelstellingenTempAlle.size());
                    });
                }
            }
        });
    }
    
    @Override
    public void update(IOefening oefening) {
        listDoelstellingenTempAlle.clear();
        List<Doelstelling> p = new ArrayList<>(dc.geefDoelstellingen());

        p.forEach((item) -> {
            listDoelstellingenTempAlle.add(item);
        });

        List<Doelstelling> m = new ArrayList<>(dc.geefDoelstellingenHuidigeOefening());
        listDoelstellingenTempGeselect = new ArrayList<>();
        m.forEach((item) -> {
            listDoelstellingenTempGeselect.add(item);
        });

        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
        //listDoelstellingenTempAlle.removeAll(listDoelstellingenTempGeselect); //werkt niet deftig, idk xd

        ArrayList<Doelstelling> h = new ArrayList<>();
        for (int j = 0; j < listDoelstellingenTempGeselect.size(); j++) {
            for (int i = 0; i < listDoelstellingenTempAlle.size(); i++) {
                if (listDoelstellingenTempAlle.get(i).toString().equals(listDoelstellingenTempGeselect.get(j).toString())) {
                    h.add(listDoelstellingenTempAlle.get(i));
                }
            }
        }
        listDoelstellingenTempAlle.removeAll(h);

        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listDoelstellingenTempGeselect.size());
        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listDoelstellingenTempAlle.size());
        
        dc.setListDoelstellingenVanOefening(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {//de lijsten blijven behouden, ze zouden moeten gaan naar oorspronkelijk voor je op toon lijst drukt
        fc.toonListview("cancel/init");
    }

    @FXML
    private void btnSubmitOnAction(ActionEvent event) {
        dc.setListDoelstellingenVanOefening(listDoelstellingenTempGeselect);
        fc.toonListview("cancel/init");
        notifyObserversList();
    }
    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
    }

    void nieuweOefening() {
        listDoelstellingenTempGeselect = new ArrayList<>();
        listDoelstellingenTempAlle = new ArrayList<>(dc.geefDoelstellingen());
        dc.setListDoelstellingenVanOefening(listDoelstellingenTempGeselect);
        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
        lblAantalGeselecteerd.setText("Aantal geselecteerd: " + listDoelstellingenTempGeselect.size());
        lblAantalBeschikbaar.setText("Aantal beschikbaar: " + listDoelstellingenTempAlle.size());
    }

    @Override
    public void updateCountGroepsb() {}

    @Override
    public void updateCountDoelst() {}

    @Override
    public void addOefeningObserver(OefeningObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeOefeningObserver(OefeningObserver o) {
        observers.remove(o);
    }
    
    private void notifyObserversList() {
        observers.forEach((observer) -> {
            observer.updateCountDoelst();
        });
    }
}