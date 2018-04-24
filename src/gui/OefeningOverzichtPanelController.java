package gui;

import domein.DomeinController;
import domein.IOefening;
import java.util.Observable;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class OefeningOverzichtPanelController extends OverzichtPanelController<IOefening> {

    private DomeinController dc;
    
    public OefeningOverzichtPanelController(DomeinController dcon) {
        super(dcon);
        this.dc = dcon;
        renderContent();
    }
    
    @Override
    void implementTableviewListener(Object newValue) {
        dc.setHuidigeOefening((IOefening)newValue);
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht oefeningen");
        setLblFilterOpText("Filter op naam:");
        getTbvOverzicht().setItems(dc.geefOefeningen());
        
        TableColumn<IOefening, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(v -> v.getValue().naamProperty());
        TableColumn<IOefening, String> col2 = new TableColumn<>("Vak");
        col2.setCellValueFactory(v -> v.getValue().getVak().naamProperty());
        getTbvOverzicht().getColumns().setAll(col1, col2);
    }

    @Override
    public void update(Observable o, Object arg) {
        renderContent();
    }

    @Override
    void btnDeleteSelectedOnAction(ActionEvent event) {
        if (getTbvOverzicht().getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Verwijder oefening");
            alert.setHeaderText("Bent u zeker dat u de oefening wilt verwijderen?");

            // Volgende regel zorgt ervoor dat het icoontje en de stylesheet meegenomen worden
            alert.initOwner(this.getScene().getWindow());
            alert.setGraphic(new ImageView(this.getClass().getResource("/gui/img/favicon.png").toString()));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                dc.verwijderOefening(getTbvOverzicht().getSelectionModel().getSelectedItem());
                renderContent();
            }
        }
    }
}
