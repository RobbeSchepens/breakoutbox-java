package domein;

import java.util.ArrayList;
import javafx.collections.ObservableList;

public class KlasController {

    private Klas huidigeKlas;
    private KlasBeheerder kb = new KlasBeheerder();

    public KlasController() {
        this.kb = new KlasBeheerder();
    }
    
    public ObservableList<IKlas> geefKlassen() {
        return kb.getKlassenFiltered();
    }

    public int geefAantalKlassen() {
        return kb.getKlassen().size();
    }

    public void setHuidigeKlas(IKlas klas) {
        this.huidigeKlas = (Klas) klas;
    }

    public void delete(IKlas o) {
        kb.delete((Klas) o);
    }

    public void voegKlasToe(String naam, ObservableList<Leerling> leerlingen) {
        ArrayList<Leerling> lln = new ArrayList<>(leerlingen);
        Klas klas = new Klas(naam, lln);
        kb.add(klas);
    }

    public void pasOefeningAan(String naam, ObservableList<Leerling> leerlingen) {
        ArrayList<Leerling> lln = new ArrayList<>(leerlingen);
        huidigeKlas.setNaam(naam);
        huidigeKlas.setLeerlingen(leerlingen);
        kb.update(huidigeKlas);
    }

    public void veranderFilter(String filterValue) {
        kb.veranderFilter(filterValue);
    }
    
}