package domein;

import javafx.collections.ObservableList;

public class ActieController {

    private Actie huidigeActie;
    private ActieBeheerder ab;

    public ActieController() {
        this.ab = new ActieBeheerder();
    }

    public ActieBeheerder getAb() {
        return ab;
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