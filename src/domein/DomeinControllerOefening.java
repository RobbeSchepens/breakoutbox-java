package domein;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DomeinControllerOefening {

    private Oefening huidigeOefening;
    private OefeningBeheerder ob;
    private FilteredList<IOefening> filteredOefeningList;
    private List<Groepsbewerking> listGroepsbewerkingenVanOefening = new ArrayList<>();
    private List<Doelstelling> listDoelstellingenVanOefening = new ArrayList<>();
    
    public DomeinControllerOefening() {
        this.ob = new OefeningBeheerder();
    }
    
    public ObservableList<IOefening> geefOefeningen() {
        return (ObservableList<IOefening>)ob.getOefeningen();
    }
    
    public int geefAantalOefeningen() {
        return ob.getOefeningen().size();
    }
    
    public void setHuidigeOefening(IOefening huidigeOefening) {
        this.huidigeOefening = (Oefening) huidigeOefening;
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
    }

    public FilteredList<IOefening> geefOefeningenFiltered() {
        return filteredOefeningList;
    }
    
    public void veranderFilter(String filterValue) {
        filteredOefeningList.setPredicate(oefening -> {
            // If filter text is empty, display all persons.
            if (filterValue == null || filterValue.isEmpty() || filterValue.equals("Alle vakken")) {
                return true;
            }
            // Compare first name and last name of every person with   
            //filter text.
            String lowerCaseValue = filterValue.toLowerCase();
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue);
        });
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

    // Wordt niet gebruikt
    public void setGroepsbewerkingenOefening(ObservableList<Groepsbewerking> selectedItems) {
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