package domein;

import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import persistentie.OefeningData;
import repository.GenericDaoJpa;
import repository.OefeningDaoJpa;

public final class OefeningBeheerder {

    //Repositories
    private OefeningDaoJpa oefRepo;
    private GenericDaoJpa<Vak> vakRepo;
    private GenericDaoJpa<Groepsbewerking> groepsbewerkingRepo;
    private GenericDaoJpa<Doelstelling> doelstellingRepo;

    // Lijsten
    private ObservableList<? extends IOefening> oefeningen;
    private FilteredList<IOefening> filteredOefeningList;
    private ObservableList<Vak> vakken;
    private ObservableList<Groepsbewerking> groepsbewerkingen;
    private ObservableList<Doelstelling> doelstellingen;
    
    public OefeningBeheerder() {
        setOefRepo(new OefeningDaoJpa());
        setVakRepo(new GenericDaoJpa(Vak.class));
        setGroepsbewerkingRepo(new GenericDaoJpa(Groepsbewerking.class));
        setDoelstellingRepo(new GenericDaoJpa(Doelstelling.class));
        
        // Seeden van database
        OefeningData od = new OefeningData(this);
    }

    public void setOefRepo(OefeningDaoJpa mock) {
        oefRepo = mock;
    }

    public void setVakRepo(GenericDaoJpa<Vak> mock) {
        vakRepo = mock;
    }

    public void setGroepsbewerkingRepo(GenericDaoJpa<Groepsbewerking> mock) {
        groepsbewerkingRepo = mock;
    }

    public void setDoelstellingRepo(GenericDaoJpa<Doelstelling> mock) {
        doelstellingRepo = mock;
    }
    
    public ObservableList<? extends IOefening> getOefeningen() {
        if (oefeningen == null) {
            oefeningen = FXCollections.observableArrayList(oefRepo.findAll());
            Collections.sort((ObservableList<Oefening>)oefeningen, Comparator.comparing(Oefening::getNaam));
            filteredOefeningList = new FilteredList<>((ObservableList<IOefening>)getOefeningen(), p -> true);
        }
        return oefeningen;
    }

    public ObservableList<IOefening> getOefeningenFiltered() {
        return filteredOefeningList;
    }
    
    public void veranderFilter(String filterValue, Vak vak) {
        filteredOefeningList.setPredicate(oefening -> {
            // If filter text is empty, display all persons.
            if (filterValue == null || filterValue.isEmpty() || filterValue.equals("Alle vakken")) {
                return true;
            }
            // Compare first name and last name of every person with   
            //filter text.
            String lowerCaseValue = filterValue.toLowerCase();
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue);
        });
    }
    
    public void add(Oefening o) {
        if (geefOefeningByNaamJpa(o.getNaam()) != null)
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        
        oefRepo.startTransaction();
        ((ObservableList<Oefening>)getOefeningen()).add(o);
        oefRepo.insert(o);
        oefRepo.commitTransaction();
    }

    public void update(Oefening o) {
        oefRepo.startTransaction();
        oefRepo.update(o);
        oefRepo.commitTransaction();
    }
    
    public void delete(Oefening o) {
        oefRepo.startTransaction();
        oefRepo.delete(o);
        getOefeningen().remove(o);
        oefRepo.commitTransaction();
    }
    
    public Oefening geefOefeningByNaamJpa(String naam) {
        return oefRepo.getOefeningByName(naam);
    }

    public ObservableList<Vak> getVakken() {
        if (vakken == null) {
            vakken = FXCollections.observableArrayList(vakRepo.findAll());
            Collections.sort(vakken, Comparator.comparing(Vak::getNaam));
        }
        return vakken;
    }

    public ObservableList<Groepsbewerking> getGroepsbewerkingen() {
        if (groepsbewerkingen == null) {
            groepsbewerkingen = FXCollections.observableArrayList(groepsbewerkingRepo.findAll());
            Collections.sort(groepsbewerkingen, Comparator.comparing(Groepsbewerking::toString));
        }
        return groepsbewerkingen;
    }

    public ObservableList<Doelstelling> getDoelstellingen() {
        if (doelstellingen == null) {
            doelstellingen = FXCollections.observableArrayList(doelstellingRepo.findAll());
            Collections.sort(doelstellingen, Comparator.comparing(Doelstelling::getDoelstelling));
        }
        return doelstellingen;
    }
    
    public void addVak(Vak o) {
        vakRepo.startTransaction();
        vakRepo.insert(o);
        vakRepo.commitTransaction();
    }

    public void addGroepsbewerking(Groepsbewerking o) {
        groepsbewerkingRepo.startTransaction();
        groepsbewerkingRepo.insert(o);
        groepsbewerkingRepo.commitTransaction();
    }

    public void addDoelstelling(Doelstelling o) {
        doelstellingRepo.startTransaction();
        doelstellingRepo.insert(o);
        doelstellingRepo.commitTransaction();
    }
}