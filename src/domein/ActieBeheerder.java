package domein;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDaoJpa;

public class ActieBeheerder implements BeheerderSubject, BeheerderObserver {

    private GenericDaoJpa<Actie> actieRepo;
    private ObservableList<? extends IActie> acties;
    private FilteredList<IActie> filteredActieList;
    private Set<BeheerderObserver> observers;

    public ActieBeheerder() {
        observers = new HashSet<>();
        setActieRepo(new GenericDaoJpa(Actie.class));
    }

    public void setActieRepo(GenericDaoJpa<Actie> mock) {
        actieRepo = mock;
    }

    public ObservableList<? extends IActie> getActies() {
        if (acties == null) {
            acties = FXCollections.observableArrayList(actieRepo.findAll());
            Collections.sort((ObservableList<Actie>) acties, Comparator.comparing(Actie::getNaam));
            filteredActieList = new FilteredList<>((ObservableList<IActie>) getActies(), p -> true);
        }
        return acties;
    }

    public ObservableList<IActie> getActiesFiltered() {
        if (filteredActieList == null) {
            getActies();
        }
        return filteredActieList;
    }

    public void veranderFilter(String filterValue) {
        filteredActieList.setPredicate(actie -> {
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }

            String lowerCaseValue = filterValue.toLowerCase();
            return actie.getNaam().toLowerCase().contains(lowerCaseValue);
        });
    }

    public void add(Actie o) {
        /*if (klasRepo.getByToString(o) != null) {
            throw new IllegalArgumentException("Er bestaat al een klas met deze naam.");
        }*/
        actieRepo.startTransaction();
        actieRepo.insert(o);
        actieRepo.commitTransaction();
        ((ObservableList<Actie>) getActies()).add(o);
        notifyObservers();
    }

    public void update(Actie o) {
        actieRepo.startTransaction();
        actieRepo.update(o);
        actieRepo.commitTransaction();
        notifyObservers();
    }

    public void delete(Actie o) {
        actieRepo.startTransaction();
        actieRepo.delete(o);
        actieRepo.commitTransaction();
        getActies().remove(o);
        notifyObservers();
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
        observers.forEach(e -> e.updateActies());
    }

    @Override public void updateOefeningen() {}
    @Override public void updateBoxes() {}
    @Override public void updateSessies() {}
    @Override public void updateActies() {}
    @Override public void updateKlassen() {}
}