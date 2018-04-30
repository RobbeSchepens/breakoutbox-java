package gui;

import domein.Doelstelling;
import domein.OefeningController;
import domein.Groepsbewerking;
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

public class DetailPanelOefeningListDoelstellingController extends VBox implements OefeningObserver, OefeningSubject {

    private OefeningController dc;
    private FrameOefeningController fc;
    private List<Doelstelling> listDoelstellingenTempAlle = new ArrayList<>();
    private List<Doelstelling> listDoelstellingenTempGeselect = new ArrayList<>();
    private int geseleceerdeDoelstellingen = 0;
    private Set<OefeningObserver> observers;

    @FXML private Label lblTitleLeftList;
    @FXML private Label lblAantalGeselecteerd;
    @FXML private Button btnDeselectAll;
    @FXML private Button btnCancel;
    @FXML private Button btnSubmit;
    @FXML private ListView<Doelstelling> lsvListAlle;
    @FXML private ListView<Doelstelling> lsvListGeselecteerde;

    public DetailPanelOefeningListDoelstellingController(OefeningController dcon, FrameOefeningController fc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPanelOefeningListDoelstelling.fxml"));
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

        List<Doelstelling> p = dc.geefDoelstellingen();
        for (Doelstelling item : p) {
            listDoelstellingenTempAlle.add(item);
        }

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
                        lblAantalGeselecteerd.setText("doelstellingen geselecteerd: " + listDoelstellingenTempGeselect.size());
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
        listDoelstellingenTempAlle.clear();
        List<Doelstelling> p = dc.geefDoelstellingen();
        for (Doelstelling item : p) {
            listDoelstellingenTempAlle.add(item);
        }


        List<Doelstelling> m = dc.geefDoelstellingenHuidigeOefening();
        listDoelstellingenTempGeselect = new ArrayList<>();
        for (Doelstelling item : m) {
            listDoelstellingenTempGeselect.add(item);
        }

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
        lblAantalGeselecteerd.setText("Doelstellingen geselecteerd: " + listDoelstellingenTempGeselect.size());
    }

    
    @FXML
    private void btnDeselectAllOnAction(ActionEvent event) {
        lsvListGeselecteerde.getSelectionModel().clearSelection();
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
        //Dit crasht bij nieuwe oefening, er bestaat nog geen huidige oefening
        //dc.setDoelstellingenOefening(lsvListGeselecteerde.getSelectionModel().getSelectedItems());
    }
    @FXML
    private void lsvListAlleOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void lsvListGeselecteerdeOnMouseClicked(MouseEvent event) {
    }

    void nieuweOefening() {
        listDoelstellingenTempGeselect = new ArrayList<>();
        listDoelstellingenTempAlle = dc.geefDoelstellingen();
        dc.setListDoelstellingenVanOefening(listDoelstellingenTempGeselect);
        lsvListAlle.setItems(FXCollections.observableArrayList(listDoelstellingenTempAlle));
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listDoelstellingenTempGeselect));
        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " + listDoelstellingenTempGeselect.size());
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
