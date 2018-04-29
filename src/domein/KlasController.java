package domein;

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
    
}