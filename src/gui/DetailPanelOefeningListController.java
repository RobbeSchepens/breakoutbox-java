package gui;

import domein.DomeinController;
import domein.Groepsbewerking;
import domein.IOefening;
import domein.OefeningObserver;
import java.io.IOException;
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
    @FXML private Label lblTitleLeftList;
    @FXML private Label lblAantalGeselecteerd;
    @FXML private Button btnDeselectAll;
    @FXML private Button btnCancel;
    @FXML private Button btnSubmit;
    @FXML private ListView<Groepsbewerking> lsvListAlle;
    @FXML private ListView<Groepsbewerking> lsvListGeselecteerde;

    public DetailPanelOefeningListController(DomeinController dcon, FrameOefeningController fc) {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("DetailPanelOefeningList.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        this.dc = dcon;
        this.fc = fc;
        lblTitleLeftList.setText("Groepsbewerkingen");
        lsvListAlle.setItems(dc.geefGroepsbewerkingen());
    }   

    @FXML
    private void btnDeselectAllOnAction(ActionEvent event) {
        lsvListGeselecteerde.getSelectionModel().clearSelection();
    }

    @Override
    public void update(IOefening oefening) {
        lblAantalGeselecteerd.setText("Groepsbewerkingen geselecteerd: " 
                + dc.geefGroepsbewerkingenHuidigeOefening().size());
        lsvListGeselecteerde.setItems(dc.geefGroepsbewerkingenHuidigeOefening());
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