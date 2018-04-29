/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import repository.GenericDaoJpa;
import repository.KlasDaoJpa;

/**
 *
 * @author Daan
 */
public class KlasBeheerder {
    private KlasDaoJpa klasRepo;
    private ObservableList<? extends IKlas> klassen;
    private List<Klas> klassenList;

    public KlasBeheerder() {

        klasRepo = new KlasDaoJpa();
        laadKlassen();

    }


    public GenericDaoJpa<Klas> getKlasRepo() {
        return klasRepo;
    }

    public void setKlasRepo(KlasDaoJpa klasRepo) {
        this.klasRepo = klasRepo;
    }

    public List<Klas> geefKlassenJPA() {

        System.out.println(klasRepo.findAll());
        return klasRepo.findAll();
    }

    public void addKlas(Klas klas) {
        klassenList.add(klas);
        klasRepo.insert(klas);
        laadKlassen();
    }

    private void laadKlassen() {
        klassenList = klasRepo.findAll();
    }

}
