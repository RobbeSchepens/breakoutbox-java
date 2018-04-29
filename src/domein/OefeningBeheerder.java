package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistentie.OefeningData;
import repository.GenericDaoJpa;
import repository.OefeningDaoJpa;

public final class OefeningBeheerder {

    public final String PERSISTENCE_UNIT_NAME = "breakoutboxPU";
    private EntityManager em;
    private EntityManagerFactory emf;
    
    //Repositories
    private OefeningDaoJpa oefRepo;
    private GenericDaoJpa<Vak> vakRepo;
    private GenericDaoJpa<Groepsbewerking> groepsbewerkingRepo;
    private GenericDaoJpa<Doelstelling> doelstellingRepo;

    // Lijsten
    private ObservableList<? extends IOefening> oefeningen;
    private ObservableList<Vak> vakken;
    private ObservableList<Groepsbewerking> groepsbewerkingen;
    private ObservableList<Doelstelling> doelstellingen;
    
    public OefeningBeheerder() {
        initializePersistentie();
        setOefRepo(new OefeningDaoJpa());
        setVakRepo(new GenericDaoJpa(Vak.class));
        setGroepsbewerkingRepo(new GenericDaoJpa(Groepsbewerking.class));
        setDoelstellingRepo(new GenericDaoJpa(Doelstelling.class));
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

    private void initializePersistentie() {
        openPersistentie(); 
        OefeningData od = new OefeningData(this);
        od.populeerData();
    }
    
    public ObservableList<? extends IOefening> getOefeningen() {
        if (oefeningen == null)
            oefeningen = FXCollections.observableArrayList(oefRepo.findAll());
        return oefeningen;
    }
    
    public void delete(Oefening o) {
        oefRepo.startTransaction();
        oefRepo.delete(o);
        getOefeningen().remove(o);
        oefRepo.commitTransaction();
    }
    
    public void add(Oefening o) {
        oefRepo.startTransaction();
        oefRepo.insert(o);
        ((ObservableList<Oefening>)getOefeningen()).add(o);
        oefRepo.commitTransaction();
    }

    public void update(Oefening o) {
        oefRepo.startTransaction();
        oefRepo.update(o);
        oefRepo.commitTransaction();
    }
    
    public Oefening geefOefeningByNaamJpa(String naam) {
        return oefRepo.getOefeningByName(naam);
    }

    public ObservableList<Vak> getVakken() {
        if (vakken == null)
            vakken = FXCollections.observableArrayList(vakRepo.findAll());
        return vakken;
    }

    public ObservableList<Groepsbewerking> getGroepsbewerkingen() {
        if (groepsbewerkingen == null)
            groepsbewerkingen = FXCollections.observableArrayList(groepsbewerkingRepo.findAll());
        return groepsbewerkingen;
    }

    public ObservableList<Doelstelling> getDoelstellingen() {
        if (doelstellingen == null)
            doelstellingen = FXCollections.observableArrayList(doelstellingRepo.findAll());
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

    
    // ================================================
    // Alle hieronder moeten herwerkt/herbekeken worden
    // ================================================
    
    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie() {
        em.close();
        emf.close();
    }
}