package domein;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDaoJpa;

public final class SessieBeheerder implements BeheerderSubject, BeheerderObserver {

    //Repositories
    private GenericDaoJpa<Sessie> sessieRepo;
    
    // Lijsten
    private ObservableList<? extends ISessie> sessies;
    private FilteredList<ISessie> filteredSessieList;
    private Set<BeheerderObserver> observers;
    
    public SessieBeheerder() {
        observers = new HashSet<>();
        setSessieRepo(new GenericDaoJpa(Sessie.class));
    }

    public void setSessieRepo(GenericDaoJpa<Sessie> mock) {
        sessieRepo = mock;
    }
    
    public ObservableList<? extends ISessie> getSessies() {
        if (sessies == null) {
            sessies = FXCollections.observableArrayList(sessieRepo.findAll());
            Collections.sort((ObservableList<Sessie>)sessies, Comparator.comparing(Sessie::getNaam));
            filteredSessieList = new FilteredList<>((ObservableList<ISessie>)getSessies(), p -> true);
        }
        return sessies;
    }

    public ObservableList<ISessie> getSessiesFiltered() {
        if (filteredSessieList == null)
            getSessies();
        return filteredSessieList;
    }
    
    public void veranderFilter(String filterValue) {
        filteredSessieList.setPredicate(e -> {
            if (filterValue == null || filterValue.isEmpty())
                return true;
            
            String lowerCaseValue = filterValue.toLowerCase();
            return e.getNaam().toLowerCase().contains(lowerCaseValue);
        });
    }
    
    public void add(Sessie o) {
        if (geefSessieByNaamJpa(o.getNaam()) != null)
            throw new IllegalArgumentException("Er bestaat al een sessie met deze naam.");
        
        sessieRepo.startTransaction();
        sessieRepo.insert(o);
        sessieRepo.commitTransaction();
        ((ObservableList<Sessie>)getSessies()).add(o);
        notifyObservers();
    }

    public void update(Sessie o) {
        sessieRepo.startTransaction();
        sessieRepo.update(o);
        sessieRepo.commitTransaction();
        notifyObservers();
    }
    
    public void delete(Sessie o) {
        sessieRepo.startTransaction();
        sessieRepo.delete(o);
        sessieRepo.commitTransaction();
        getSessies().remove(o);
        notifyObservers();
    }
    
    public Oefening geefSessieByNaamJpa(String naam) {
        return null;
        //return sessieRepo.getOefeningByName(naam);
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
        observers.forEach(e -> e.updateSessies());
    }

    @Override public void updateOefeningen() {}
    @Override public void updateBoxes() {
        //boxes = null;
    }
    @Override public void updateSessies() {}
    @Override public void updateActies() {}
    @Override public void updateKlassen() {
        //klassen = null;
    }
}