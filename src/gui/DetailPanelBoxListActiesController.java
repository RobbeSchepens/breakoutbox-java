package gui;

import domein.BoxController;
import domein.BoxObserver;
import domein.BoxSubject;
import domein.IActie;
import domein.IBox;
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

public class DetailPanelBoxListActiesController extends VBox implements BoxObserver, BoxSubject {

    FrameBoxController fc;
    BoxController bc;
    private List<IActie> listActiesTempAlle;
    private List<IActie> listActiesTempGeselect = new ArrayList<>();
    private Set<BoxObserver> observers;

    @FXML private Label lblTitleLeftList;
    @FXML private Label lblAantalGeselecteerd;
    @FXML private ListView<IActie> lsvListAlle;
    @FXML private ListView<IActie> lsvListGeselecteerde;
    @FXML private Button btnDeselectAll;
    @FXML private Button btnCancel;
    @FXML private Button btnSubmit;

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
        this.observers = new HashSet<>();

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
        lblAantalGeselecteerd.setText("Acties geselecteerd: " + box.getActies().size());
        List<? extends IActie> lt = box.getActies();
        listActiesTempGeselect = (List<IActie>) lt;

        bc.setListActiesVanBox(listActiesTempGeselect);

        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listActiesTempGeselect));

        //listActiesTempAlle.removeAll(lt);
        lsvListAlle.setItems(FXCollections.observableArrayList(listActiesTempAlle));
        


    }

    public void nieuweBox() {

        listActiesTempAlle = new ArrayList<>(bc.geefActies());
        listActiesTempGeselect = new ArrayList<>();
        lsvListAlle.setItems(FXCollections.observableArrayList(listActiesTempAlle));
        lsvListGeselecteerde.setItems(FXCollections.observableArrayList(listActiesTempGeselect));
        bc.setListActiesVanBox(listActiesTempGeselect);
        lblAantalGeselecteerd.setText("Geselecteerde Oefeningen 0");
        
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
        bc.setListActiesVanBox(listActiesTempGeselect);
        fc.toonListview("cancel/init");
        notifyObserversList();
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
            observer.updateCountActies();
        });
    }

    @Override
    public void updateCountActies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCountOefeningen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
