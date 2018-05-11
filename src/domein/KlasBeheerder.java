package domein;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDaoJpa;

public class KlasBeheerder implements BeheerderSubject, BeheerderObserver {
    
    //Repositories
    private GenericDaoJpa<Klas> klasRepo;

    // Lijsten
    private ObservableList<? extends IKlas> klassen;
    private FilteredList<IKlas> filteredKlasList;
    private Set<BeheerderObserver> observers;
    
    public KlasBeheerder() {
        observers = new HashSet<>();
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

        checkOpDubbel(o);
        klasRepo.startTransaction();
        klasRepo.insert(o);
        klasRepo.commitTransaction();
        ((ObservableList<Klas>)getKlassen()).add(o);
        notifyObservers();
    }

    public void update(Klas o) {
        checkOpDubbel(o);
        klasRepo.startTransaction();
        klasRepo.update(o);
        klasRepo.commitTransaction();
        notifyObservers();
    }
    
    public void delete(Klas o) {
        klasRepo.startTransaction();
        klasRepo.delete(o);
        klasRepo.commitTransaction();
        getKlassen().remove(o);
        notifyObservers();
    }

    public void checkOpDubbel(Klas o) {
        getKlassen().forEach(item -> {
            if (item.getNaam().equals(o.getNaam())) {
                if (!(item == o)) {
                    throw new IllegalArgumentException("Deze klas naam is al in gebruik");
                }
            }
        });

    }

    @Override
    public void addBeheerderObserver(BeheerderObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeBeheerderObserver(BeheerderObserver o) {
        observers.remove(o);
    }
    
    private void notifyObservers() {
        observers.forEach(e -> e.updateKlassen());
    }

    @Override public void updateOefeningen() {}
    @Override public void updateBoxes() {}
    @Override public void updateSessies() {}
    @Override public void updateActies() {}
    @Override public void updateKlassen() {}
}