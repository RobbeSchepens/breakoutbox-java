package domein;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistentie.OefeningData;
import repository.GenericDao;
import repository.GenericDaoJpa;
import repository.OefeningDao;
import repository.OefeningDaoJpa;

public final class OefeningBeheerder {

    public final String PERSISTENCE_UNIT_NAME = "breakoutboxPU";
    private EntityManager em;
    private EntityManagerFactory emf;
    private OefeningDaoJpa oefRepo;

    //extra repos
    private GenericDao<Vak> vakRepo;
    private GenericDao<Groepsbewerking> groepsbewerkingRepo;
    private GenericDao<Doelstelling> doeslstellingRepo;

    private ObservableList<? extends IOefening> oefeningen;
    
    public OefeningBeheerder() {
        initializePersistentie();
        setOefRepo(new OefeningDaoJpa());
        setVakRepo(new GenericDaoJpa(Vak.class));
        setGroepsbewerkingRepo(new GenericDaoJpa(Groepsbewerking.class));
        setDoeslstellingRepo(new GenericDaoJpa(Doelstelling.class));
    }

    public void setOefRepo(OefeningDaoJpa mock) {
        oefRepo = mock;
    }

    public void setVakRepo(GenericDao<Vak> mock) {
        vakRepo = mock;
    }

    public void setGroepsbewerkingRepo(GenericDao<Groepsbewerking> mock) {
        groepsbewerkingRepo = mock;
    }

    public void setDoeslstellingRepo(GenericDao<Doelstelling> mock) {
        doeslstellingRepo = mock;
    }

    private void initializePersistentie() {
        openPersistentie(); 
        OefeningData od = new OefeningData(this);
        od.populeerData();
    }
    
    ObservableList<? extends IOefening> getOefeningen() {
        if (oefeningen == null)
            oefeningen = FXCollections.observableArrayList(oefRepo.findAll());
        return oefeningen;
    }
    
    void delete(Oefening o) {
        oefRepo.startTransaction();
        getOefeningen().remove(o);
        oefRepo.commitTransaction();
    }
    
    void add(Oefening o) {
        oefRepo.startTransaction();
        ((ObservableList<Oefening>)getOefeningen()).add(o);
        oefRepo.commitTransaction();
    }

    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie() {
        em.close();
        emf.close();
    }

    // Methodes voor oefening op te halen, zoals gefilterd etc
    // Voor meer voorbeelden, zie OOPIII_JPA_Garage 
    public void addOefening(Oefening o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public List<Oefening> geefOefeningenJPA() {
        return oefRepo.findAll();
    }

    public OefeningDao getOefRepo() {
        return oefRepo;
    }

    public List<Vak> geefVakkenJPA() {
        return vakRepo.findAll();
    }

    public List<Groepsbewerking> geefGroepsbewerkingenJPA() {
        return groepsbewerkingRepo.findAll();
    }

    public List<Doelstelling> geefDoelstellingenJPA() {
        return doeslstellingRepo.findAll();
    }

    public Oefening geefOefeningByNaamJpa(String naam) {
        return oefRepo.getOefeningByName(naam);
    }
    
    public void pasOefeningAan(Oefening o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public void updateOefening(Oefening o) {
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

    public void deleteOefening(Oefening o) {
        em.getTransaction().begin();
        if (!em.contains(o)) {
            o = em.merge(o);
        }
        em.remove(o);
        em.getTransaction().commit();
    }

    /////////////
    public void addVak(Vak o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public void addGroepsbewerking(Groepsbewerking o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public void addDoelstelling(Doelstelling o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

}
