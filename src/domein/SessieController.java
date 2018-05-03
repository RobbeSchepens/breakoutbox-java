package domein;

import java.time.LocalDate;
import javafx.collections.ObservableList;

public class SessieController {

    private Sessie huidigeSessie;
    private SessieBeheerder sb;

    public SessieController() {
        this.sb = new SessieBeheerder();
    }

    public SessieBeheerder getSb() {
        return sb;
    }
    
    public ObservableList<ISessie> geefSessies() {
        return sb.getSessiesFiltered();
    }
    
    public int geefAantalSessies() {
        return sb.getSessies().size();
    }
    
    public void setHuidigeSessie(ISessie huidigeSessie) {
        this.huidigeSessie = (Sessie) huidigeSessie;
    }

    public ISessie getHuidigeSessie() {
        return huidigeSessie;
    }
    
    public void voegNieuweSessieToe(String naam, String omschrijving, LocalDate startdatum) {
        Sessie sessie = new Sessie(naam, omschrijving, startdatum);
        sb.add(sessie);
    }

    public void pasSessieAan(String naam, String omschrijving, LocalDate startdatum) {
        // Check of de naam al bestaat in de database
        if (!huidigeSessie.getNaam().equals(naam) && sb.geefSessieByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een sessie met deze naam.");
        }

        huidigeSessie.setNaam(naam);
        huidigeSessie.setOmschrijving(omschrijving);
        huidigeSessie.setStartdatum(startdatum);
        sb.update(huidigeSessie);
    }
    
    public void delete(ISessie o) {
        sb.delete((Sessie) o);
    }
    
    public void veranderFilter(String filterValue) {
        sb.veranderFilter(filterValue);
    }
    
}