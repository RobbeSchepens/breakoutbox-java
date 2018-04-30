/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.GenericDaoJpa;

/**
 *
 * @author Daan
 */
public class ActieBeheerder {

    private GenericDaoJpa<Actie> actieRepo;
    private ObservableList<? extends IActie> acties;

    public ActieBeheerder() {
        setActieRepo(new GenericDaoJpa(Actie.class));
    }

    public void setActieRepo(GenericDaoJpa<Actie> mock) {
        actieRepo = mock;
    }

    public ObservableList<? extends IActie> getActies() {
        if (acties == null) {
            acties = FXCollections.observableArrayList(actieRepo.findAll());
        }
        return acties;
    }

    public void add(Actie o) {
        /*if (klasRepo.getByToString(o) != null) {
            throw new IllegalArgumentException("Er bestaat al een klas met deze naam.");
        }*/
        actieRepo.startTransaction();
        ((ObservableList<Actie>) getActies()).add(o);
        actieRepo.insert(o);
        actieRepo.commitTransaction();
    }

    public void update(Actie o) {

        actieRepo.startTransaction();
        actieRepo.update(o);
        actieRepo.commitTransaction();
    }

    public void delete(Actie o) {
        actieRepo.startTransaction();
        getActies().remove(o);
        actieRepo.delete(o);
        actieRepo.commitTransaction();
    }



}
