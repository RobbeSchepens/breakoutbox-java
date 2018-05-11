package domein;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDaoJpa;
import repository.OefeningDaoJpa;

public final class BoxBeheerder implements BeheerderSubject, BeheerderObserver {

    //Repositories
    private GenericDaoJpa<Box> boxRepo;
    private GenericDaoJpa<Actie> actieRepo;
    private OefeningDaoJpa oefeningRepo;
    private GenericDaoJpa<Vak> vakRepo;

    // Lijsten
    private ObservableList<? extends IBox> boxen;
    private FilteredList<IBox> filteredBoxList;
    private ObservableList<Vak> vakken;
    private ObservableList<? extends IOefening> oefeningen;
    private ObservableList<? extends IActie> acties;
    private Set<BeheerderObserver> observers;

    public BoxBeheerder() {
        observers = new HashSet<>();
        setBoxRepo(new GenericDaoJpa(Box.class));
        setVakRepo(new GenericDaoJpa(Vak.class));
        setActieRepo(new GenericDaoJpa(Actie.class));
        setOefeningRepo(new OefeningDaoJpa()); // Een tweede repo aanmaken, mss geen goed idee? (ook in OefBeheerder)
    }

    public void setBoxRepo(GenericDaoJpa<Box> boxRepo) {
        this.boxRepo = boxRepo;
    }

    public void setActieRepo(GenericDaoJpa<Actie> actieRepo) {
        this.actieRepo = actieRepo;
    }

    public void setOefeningRepo(OefeningDaoJpa oefeningRepo) {
        this.oefeningRepo = oefeningRepo;
    }

    public void setVakRepo(GenericDaoJpa<Vak> vakRepo) {
        this.vakRepo = vakRepo;
    }

    public ObservableList<? extends IBox> getBoxen() {
        if (boxen == null) {
            boxen = FXCollections.observableArrayList(boxRepo.findAll());
            Collections.sort((ObservableList<Box>)boxen, Comparator.comparing(Box::getNaam));
            filteredBoxList = new FilteredList<>((ObservableList<IBox>)getBoxen(), p -> true);
        }
        return boxen;
    }

    public ObservableList<IBox> getBoxenFiltered() {
        if (filteredBoxList == null)
            getBoxen();
        return filteredBoxList;
    }
    
    public void veranderFilter(String filterValue) {
        filteredBoxList.setPredicate(oefening -> {
            if (filterValue == null || filterValue.isEmpty())
                return true;
            
            String lowerCaseValue = filterValue.toLowerCase();
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue);
        });
    }

    public ObservableList<Vak> getVakken() {
        if (vakken == null) {
            vakken = FXCollections.observableArrayList(vakRepo.findAll());
            Collections.sort(vakken, Comparator.comparing(Vak::getNaam));
        }
        return vakken;
    }

    public ObservableList<? extends IOefening> getOefeningen() {
        if (oefeningen == null) {
            oefeningen = FXCollections.observableArrayList(oefeningRepo.findAll());
            Collections.sort((ObservableList<Oefening>)oefeningen, Comparator.comparing(Oefening::getNaam));
        }
        return oefeningen;
    }

    public ObservableList<? extends IActie> getActies() {
        if (acties == null) {
            acties = FXCollections.observableArrayList(actieRepo.findAll());
            Collections.sort((ObservableList<Actie>)acties, Comparator.comparing(Actie::getNaam));
        }
        return acties;
    }

    public void add(Box o) {
        checkOpDubbel(o);

        boxRepo.startTransaction();
        boxRepo.insert(o);
        boxRepo.commitTransaction();
        ((ObservableList<Box>) getBoxen()).add(o);
        notifyObservers();
    }

    public void update(Box o) {
        checkOpDubbel(o);
        boxRepo.startTransaction();
        boxRepo.update(o);
        boxRepo.commitTransaction();
        notifyObservers();
    }

    public void delete(Box o) {
        boxRepo.startTransaction();
        boxRepo.delete(o);
        boxRepo.commitTransaction();
        getBoxen().remove(o);
        notifyObservers();
    }

    public void checkOpDubbel(Box o) {

        getBoxen().forEach(item -> {
            if (item.getNaam().equals(o.getNaam())) {
                if (!(item == o)) {
                    throw new IllegalArgumentException("Deze naam is al in gebruik");
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
        observers.forEach(e -> e.updateBoxes());
    }

    @Override public void updateOefeningen() {
        oefeningen = null;
    }
    @Override public void updateBoxes() {}
    @Override public void updateSessies() {}
    @Override public void updateActies() {
        acties = null;
    }
    @Override public void updateKlassen() {}
}