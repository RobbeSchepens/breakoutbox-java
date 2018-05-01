package domein;

import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDaoJpa;

public class KlasBeheerder {
    
    //Repositories
    private GenericDaoJpa<Klas> klasRepo;

    // Lijsten
    private ObservableList<? extends IKlas> klassen;
    private FilteredList<IKlas> filteredKlasList;
    
    public KlasBeheerder() {
        setKlasRepo(new GenericDaoJpa(Klas.class));

    }

    public void setKlasRepo(GenericDaoJpa<Klas> mock) {
        klasRepo = mock;
    }
    
    public ObservableList<? extends IKlas> getKlassen() {
        if (klassen == null) {
            klassen = FXCollections.observableArrayList(klasRepo.findAll());
            Collections.sort((ObservableList<Klas>) klassen, Comparator.comparing(Klas::getNaam));
            filteredKlasList = new FilteredList<>((ObservableList<IKlas>) getKlassen(), p -> true);
        }
        return klassen;
    }

    public ObservableList<IKlas> getKlassenFiltered() {
        if (filteredKlasList == null) {
            getKlassen();
        }
        return filteredKlasList;
    }

    public void veranderFilter(String filterValue) {
        filteredKlasList.setPredicate(oefening -> {
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }

            String lowerCaseValue = filterValue.toLowerCase();
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue);
        });
    }

    public void add(Klas o) {
        /*if (klasRepo.getByToString(o) != null) {
            throw new IllegalArgumentException("Er bestaat al een klas met deze naam.");
        }*/

        klasRepo.startTransaction();
        ((ObservableList<Klas>)getKlassen()).add(o);
        klasRepo.insert(o);
        klasRepo.commitTransaction();
    }

    public void update(Klas o) {

        klasRepo.startTransaction();
        klasRepo.update(o);
        klasRepo.commitTransaction();
    }
    
    public void delete(Klas o) {
        klasRepo.startTransaction();
        klasRepo.delete(o);
        getKlassen().remove(o);
        klasRepo.commitTransaction();
    }
}
