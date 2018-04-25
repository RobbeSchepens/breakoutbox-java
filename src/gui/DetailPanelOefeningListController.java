package gui;

import domein.DomeinController;
import domein.Groepsbewerking;
import domein.IOefening;
import domein.OefeningObserver;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

public class DetailPanelOefeningListController extends VBox implements OefeningObserver {

    private DomeinController dc;
    @FXML private Label lblTitleLeftList;
    @FXML private Label lblAantalGeselecteerd;
    @FXML private ListView<Groepsbewerking> lsvList;
    @FXML private Button btnDeselectAll;

    public DetailPanelOefeningListController(DomeinController dcon) {
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
        lblTitleLeftList.setText("Groepsbewerkingen");
        lsvList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }   

    @FXML
    private void btnDeselectAllOnAction(ActionEvent event) {
        lsvList.getSelectionModel().clearSelection();
    }

    @Override
    public void update(IOefening oefening) {
        lsvList.setItems(dc.geefGroepsbewerkingen());
    }
}