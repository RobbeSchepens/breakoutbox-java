package domein;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.ObservableList;

public class KlasController {

    private Klas huidigeKlas;
    private KlasBeheerder kb = new KlasBeheerder();

    public KlasController() {
        this.kb = new KlasBeheerder();
    }
    
    public ObservableList<IKlas> geefKlassen() {
        return (ObservableList<IKlas>)kb.getKlassen();
    }

    public void setHuidigeKlas(IKlas klas) {
        this.huidigeKlas = (Klas) klas;

    }

    public void voegKlasToe(String naam, ObservableList<Leerling> leerlingen) {
        ArrayList<Leerling> lln = new ArrayList<>(leerlingen);
        Klas klas = new Klas(naam, lln);
        kb.add(klas);
    }

    public void pasOefeningAan(String text, ObservableList<Leerling> items) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}