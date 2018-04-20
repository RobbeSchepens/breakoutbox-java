package domein;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistentie.OefeningData;
import repository.GenericDao;
import repository.GenericDaoJpa;

public final class OefeningBeheerder {
    public final String PERSISTENCE_UNIT_NAME = "breakoutboxPU";
    private EntityManager em;
    private EntityManagerFactory emf;
    private Map<String, Oefening> oefeningenMap = new HashMap<>();
    private GenericDao<Oefening> oefRepo;

    public OefeningBeheerder() {
        initializePersistentie();
        setOefRepo(new GenericDaoJpa<>(Oefening.class));
    }
    
    public void setOefRepo(GenericDao<Oefening> mock){
        oefRepo = mock;
    }
    
    private void initializePersistentie() {
        openPersistentie();
        OefeningData od = new OefeningData(this);
        od.populeerData();
    }
    
    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie(){
         em.close();
         emf.close();
    }
    
    // Methodes voor oefening op te halen, zoals gefilterd etc
    // Voor meer voorbeelden, zie OOPIII_JPA_Garage 
    
    public void addOefening(Oefening o) {
        oefeningenMap.put(o.getNaam(), o);
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public List<Oefening> geefOefeningenJPA() {
        return oefRepo.findAll();
    }
}
