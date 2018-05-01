/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Daan
 */
public class ActieController {

    private Actie huidigeActie;
    ActieBeheerder ab;


    public ActieController() {
        this.ab = new ActieBeheerder();
    }

    public ObservableList<IActie> geefActies() {
        return ab.getActiesFiltered();
    }

    public int geefAantalActies() {
        return ab.getActies().size();
    }

    public void setHuidigeKlas(IActie actie) {
        this.huidigeActie = (Actie) actie;

    }

    public void voegActieToe(String naam) {
        Actie actie = new Actie(naam);

        ab.add(actie);
    }

    public void delete(IActie o) {
        ab.delete((Actie) o);
    }

    public void veranderFilter(String filterValue) {
        ab.veranderFilter(filterValue);
    }

    public void pasActieAan(String naam) {
        huidigeActie.setNaam(naam);
        ab.update(huidigeActie);

    }


}
