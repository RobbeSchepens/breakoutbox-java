/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import repository.GenericDaoJpa;

/**
 *
 * @author Daan
 */
public class KlasBeheerder {
    public final String PERSISTENCE_UNIT_NAME = "breakoutboxPU";
    private EntityManager em;
    private EntityManagerFactory emf;
    private GenericDaoJpa<Klas> klasRepo;


    private ObservableList<? extends IKlas> klassen;

    public KlasBeheerder() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();

        setKlasRepo(new GenericDaoJpa(Klas.class));
    }

    public GenericDaoJpa<Klas> getKlasRepo() {
        return klasRepo;
    }

    public void setKlasRepo(GenericDaoJpa<Klas> klasRepo) {
        this.klasRepo = klasRepo;
    }

    public List<Klas> geefKlassenJPA() {
        System.out.println("alle klassen");
        System.out.println(klasRepo.findAll());
        return klasRepo.findAll();
    }

    public void addKlas(Klas klas) {
        em.getTransaction().begin();
        em.persist(klas);
        em.getTransaction().commit();
    }


}
