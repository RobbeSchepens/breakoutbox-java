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
        OefeningData od = new OefeningData(this, new BoxBeheerder());
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
        if (filteredOefeningList == null)
            getOefeningen();
        return filteredOefeningList;
    }
    
    public void veranderFilter(String filterValue, Vak vak) {
        filteredOefeningList.setPredicate(oefening -> {
            // Als filtervalue of vak null/empty is, toon alle oefeningen
            if (filterValue == null || filterValue.isEmpty() && vak == null)
                return true;
            
            // Filter op vak als naam null/empty is
            if (filterValue == null || filterValue.isEmpty())
                return oefening.getVak() == vak;
            
            String lowerCaseValue = filterValue.toLowerCase();
            
            // Filter enkel op naam als vak null is
            if (vak == null)
                return oefening.getNaam().toLowerCase().contains(lowerCaseValue);
            
            // Filter op beiden
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue) && oefening.getVak() == vak;
        });
        System.out.println(vak);
        System.out.println(filteredOefeningList);
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