package gui;

import domein.ISessie;
import domein.SessieController;
import domein.SessieObserver;
import domein.SessieSubject;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public final class SessieOverzichtPanelController extends OverzichtPanelController<ISessie, SessieController> implements SessieSubject {

    private final SessieController sc;
    private final SessieFrameController fc;
    private final Set<SessieObserver> observers;

    public SessieOverzichtPanelController(SessieController sc, SessieFrameController fc) {
        super(sc);

        observers = new HashSet<>();
        this.sc = sc;
        this.fc = fc;
        renderContent();
    }

    @Override
    void btnDeleteSelectedOnAction() {
        if (getTbvOverzicht().getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Verwijder sessie");
            alert.setHeaderText("Bent u zeker dat u de sessie wilt verwijderen?");

            // Volgende regel zorgt ervoor dat het icoontje en de stylesheet meegenomen worden
            alert.initOwner(this.getScene().getWindow());
            alert.setGraphic(new ImageView(this.getClass().getResource("/gui/img/favicon.png").toString()));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                sc.delete(getTbvOverzicht().getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    <T> void implementTableviewListener(T newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void renderContent() {
        setLblTitleLeftText("Overzicht sessies");
        setLblFilterOpText("Filter op naam:");
        renderTable();
    }

    @Override
    void filter(String newValue) {
        sc.veranderFilter(newValue);
    }

    @Override
    void initNieuw() {
        fc.initNieuw();
        renderTable();
    }

    private void renderTable() {
        // Set items for tableview
        getTbvOverzicht().setItems(sc.geefSessies());
        
        // Create new columns based on current class
        TableColumn<ISessie, String> col1 = new TableColumn<>("Naam");
        col1.setCellValueFactory(v -> v.getValue().naamProperty());

        TableColumn<ISessie, String> col2 = new TableColumn<>("Klas");
        col2.setCellValueFactory(v -> v.getValue().getKlas().naamProperty());

        TableColumn<ISessie, LocalDate> col3 = new TableColumn<>("Startdatum");
        //col2.setCellValueFactory(v -> v.getValue().getStartdatum().toString());
        
        // Add the columns to the tableview
        getTbvOverzicht().getColumns().setAll(col1, col2, col3);
    }

    @Override
    void clearAddedFilters() {}
    
    @Override
    public void addSessieObserver(SessieObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeSessieObserver(SessieObserver o) {
        observers.remove(o);
    }
    
    private void notifyObservers() {
        ISessie o = getTbvOverzicht().getSelectionModel().getSelectedItem();
        observers.forEach((observer) -> {
            observer.update(o);
        });
    }
}