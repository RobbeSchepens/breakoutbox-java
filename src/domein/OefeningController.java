package domein;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class OefeningController {

    private Oefening huidigeOefening;
    private OefeningBeheerder ob;
    private List<Groepsbewerking> listGroepsbewerkingenVanOefening = new ArrayList<>();
    private List<Doelstelling> listDoelstellingenVanOefening = new ArrayList<>();
    
    public OefeningController() {
        ob = new OefeningBeheerder();
        huidigeOefening = new OefeningNull();
    }
    
    public ObservableList<IOefening> geefOefeningen() {
        return ob.getOefeningenFiltered();
    }
    
    public int geefAantalOefeningen() {
        return ob.getOefeningen().size();
    }
    
    public void setHuidigeOefening(IOefening huidigeOefening) {
        this.huidigeOefening = (Oefening) huidigeOefening;
    }

    public IOefening getHuidigeOefening() {
        return huidigeOefening;
    }
    
    public void voegNieuweOefeningToe(String naam, String antwoord, File opgave, File feedback, Vak vak) {
        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        ob.add(oefening);
    }

    public void pasOefeningAan(String naam, String antwoord, File opgave, File feedback, Vak vak) {
        // Check of de naam al bestaat in de database
        if (!huidigeOefening.getNaam().equals(naam) && ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }
        // De groepsbewerkingen zijn onveranderd gebleven
        if (listGroepsbewerkingenVanOefening.isEmpty()) {
            listGroepsbewerkingenVanOefening = huidigeOefening.getGroepsBewerkingen();
        }
        // De doelstellingen zijn onveranderd gebleven
        if (listDoelstellingenVanOefening.isEmpty()) {
            listDoelstellingenVanOefening = huidigeOefening.getDoelstellingen();
        }
        huidigeOefening.roepSettersAan(naam, antwoord, vak, opgave, feedback, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        ob.update(huidigeOefening);
    }
    
    public void delete(IOefening o) {
        ob.delete((Oefening) o);
        huidigeOefening = new OefeningNull();
    }
    
    public void veranderFilter(String filterValue) {
        ob.veranderFilter(filterValue);
    }
    
    public ObservableList<Vak> geefVakken() {
        return ob.getVakken();
    }
    
    public ObservableList<Groepsbewerking> geefGroepsbewerkingen() {
        return ob.getGroepsbewerkingen();
    }
    
    public ObservableList<Doelstelling> geefDoelstellingen() {
        return ob.getDoelstellingen();
    }
    
    public void setListGroepsbewerkingenVanOefening(List<Groepsbewerking> listGroepsbewerkingenVanOefening) {
        this.listGroepsbewerkingenVanOefening = listGroepsbewerkingenVanOefening;
    }

    public void setListDoelstellingenVanOefening(List<Doelstelling> listDoelstellingenVanOefening) {
        this.listDoelstellingenVanOefening = listDoelstellingenVanOefening;
    }
    
    public int getAantalTempGroepsbewerkingen() {
        return this.listGroepsbewerkingenVanOefening.size();
    }
    
    public int getAantalTempDoelstellingen() {
        return this.listDoelstellingenVanOefening.size();
    }

    // Wordt niet gebruikt
    public void setGroepsbewerkingenOefening(List<Groepsbewerking> selectedItems) {
        huidigeOefening.setGroepsbewerkingen(selectedItems);
    }

    // Wordt niet gebruikt
    public void setDoelstellingenOefening(ObservableList<Doelstelling> selectedItems) {
        huidigeOefening.setDoelstellingen(selectedItems);
    }
    
    public ObservableList<Groepsbewerking> geefGroepsbewerkingenHuidigeOefening() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(huidigeOefening.getGroepsBewerkingen()));
    }
    
    public ObservableList<Doelstelling> geefDoelstellingenHuidigeOefening() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(huidigeOefening.getDoelstellingen()));
    }
}