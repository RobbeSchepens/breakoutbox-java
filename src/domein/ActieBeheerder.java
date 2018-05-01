/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDaoJpa;

/**
 *
 * @author Daan
 */
public class ActieBeheerder {

    private GenericDaoJpa<Actie> actieRepo;
    private ObservableList<? extends IActie> acties;
    private FilteredList<IActie> filteredActieList;

    public ActieBeheerder() {
        setActieRepo(new GenericDaoJpa(Actie.class));
    }

    public void setActieRepo(GenericDaoJpa<Actie> mock) {
        actieRepo = mock;
    }

    public ObservableList<? extends IActie> getActies() {
        if (acties == null) {
            acties = FXCollections.observableArrayList(actieRepo.findAll());
            Collections.sort((ObservableList<Actie>) acties, Comparator.comparing(Actie::getNaam));
            filteredActieList = new FilteredList<>((ObservableList<IActie>) getActies(), p -> true);
        }
        return acties;
    }

    public ObservableList<IActie> getActiesFiltered() {
        if (filteredActieList == null) {
            getActies();
        }
        return filteredActieList;
    }

    public void veranderFilter(String filterValue) {
        filteredActieList.setPredicate(actie -> {
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }

            String lowerCaseValue = filterValue.toLowerCase();
            return actie.getNaam().toLowerCase().contains(lowerCaseValue);
        });
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
