package domein;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class BoxBeheerder {
    public final String PERSISTENCE_UNIT_NAME = "breakoutboxPU";
    private EntityManager em;
    private EntityManagerFactory emf;

    public BoxBeheerder() {
        initializePersistentie();
    }
    
    private void initializePersistentie() {
        openPersistentie();
        //BoxData od = new BoxData(this);
        //od.populeerData();
    }
    
    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public void closePersistentie(){
         em.close();
         emf.close();
    }
}
