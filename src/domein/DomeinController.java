package domein;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DomeinController extends Observable {

    // Oefening
    private Oefening huidigeOefening;
    private OefeningBeheerder ob = new OefeningBeheerder();
    private List<Oefening> oefeningLijst;
    private FilteredList<IOefening> filteredOefeningList;
    private List<Vak> vakkenLijst;
    private List<Groepsbewerking> groepsbewerkingenLijst;
    private List<Doelstelling> doelstellingenLijst;

    private List<Groepsbewerking> listGroepsbewerkingenVanOefening = new ArrayList<>();
    private List<Doelstelling> listDoelstellingenVanOefening = new ArrayList<>();
    
    // Box
    //private Box huidigeBox;
    
    // Sessie
    //private Sessie huidigeSessie;

    public DomeinController() {
        this.vakkenLijst = ob.geefVakkenJPA();
        this.groepsbewerkingenLijst = ob.geefGroepsbewerkingenJPA();
        this.doelstellingenLijst = ob.geefDoelstellingenJPA();
        sortAllLists();
    }

    private void sortAllLists() {
        Collections.sort(vakkenLijst, Comparator.comparing(Vak::getNaam));
        Collections.sort(groepsbewerkingenLijst, Comparator.comparing(Groepsbewerking::toString));
        Collections.sort(doelstellingenLijst, Comparator.comparing(Doelstelling::getDoelstelling));
    }

    // ================
    // == Oefeningen ==
    // ================
    private List<Oefening> getOefeningList() {
        if (oefeningLijst == null) {
            oefeningLijst = ob.geefOefeningenJPA();
            filteredOefeningList = new FilteredList<>(geefOefeningen(), p -> true);
        }
        return oefeningLijst;
    }

    public void setListGroepsbewerkingenVanOefening(List<Groepsbewerking> listGroepsbewerkingenVanOefening) {
        this.listGroepsbewerkingenVanOefening = listGroepsbewerkingenVanOefening;
    }

    public void setListDoelstellingenVanOefening(List<Doelstelling> listDoelstellingenVanOefening) {
        this.listDoelstellingenVanOefening = listDoelstellingenVanOefening;
    }


    public boolean noOefeningen() {
        return getOefeningList().isEmpty();
    }

    public int geefAantalOefeningen() {
        return getOefeningList().size();
    }

    public List<String> geefOefeningNaamLijst() {
        return getOefeningList().stream().map(Oefening::getNaam).collect(Collectors.toList());
    }

    public ObservableList<IOefening> geefOefeningen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(getOefeningList()));
    }

    public FilteredList<IOefening> geefOefeningenFiltered() {
        return filteredOefeningList;
    }

    public IOefening geefOefeningByNaam(String naam) {
        return ob.geefOefeningByNaamJpa(naam);
    }

    // Weg na refactor
    public IOefening getHuidigeOefening() {
        return huidigeOefening;
    }

    public void setHuidigeOefening(IOefening huidigeOefening) {
        this.huidigeOefening = (Oefening) huidigeOefening;
    }

    public void veranderFilter(String filterValue) {
        filteredOefeningList.setPredicate(oefening -> {
            // If filter text is empty, display all persons.
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with   
            //filter text.
            String lowerCaseValue = filterValue.toLowerCase();
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue);
        });
    }

    public void verwijderOefening(IOefening o) {
        ob.deleteOefening((Oefening) o);
        getOefeningList().remove((Oefening) o);
        setChanged();
        notifyObservers(getOefeningList());
    }

    public void voegNieuweOefeningToe(String naam, String antwoord, File opgave, File feedback, Vak vak) {
        if (ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }
        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        getOefeningList().add(oefening);
        System.out.println(getOefeningList());
        ob.addOefening(oefening);
        setChanged();
        notifyObservers();
    }

    public void pasOefeningAan(String naam, String antwoord, File opgave, File feedback, Vak vak) {
        if (!huidigeOefening.getNaam().equals(naam) && ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }

        huidigeOefening.roepSettersAan(naam, antwoord, vak, opgave, feedback, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        ob.updateOefening(huidigeOefening);
        setChanged();
        notifyObservers();
    }

    /*public void voegNieuweOefeningToe(String naam, String antwoord, Vak vak,
            File opgave, String opgaveNaam,
            File feedback, String feedbackNaam,
            List<Groepsbewerking> groepsbewerkingen,
            List<Doelstelling> doelstellingen) {
        
        if (ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }

        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, opgaveNaam,
                feedback, feedbackNaam, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        getOefeningList().add(oefening);
        ob.addOefening(oefening);
        setChanged();
        notifyObservers();
    }*/
    
    /*public void pasOefeningAan(String naam, String antwoord, Vak vak,
            File opgave, String opgaveNaam,
            File feedback, String feedbackNaam,
            /*List<Groepsbewerking> groepsbewerkingen,
            List<Doelstelling> doelstellingen ) {
        if (!huidigeOefening.getNaam().equals(naam) && ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }
        groepsbewerkingen = huidigeOefening.getGroepsBewerkingen();
        doelstellingen =  huidigeOefening.getDoelstellingen();
        huidigeOefening.roepSettersAan(naam, antwoord, vak, opgave, opgaveNaam,
                feedback, feedbackNaam, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        ob.updateOefening(huidigeOefening);
        setChanged();
        notifyObservers();
    }*/

    // =====================================================
    // == Vakken, Groepsbewerkingen en Doelstellingen ======
    // =====================================================
    public List<Vak> getVakkenList() {
        if (vakkenLijst == null) {
            vakkenLijst = ob.geefVakkenJPA();
        }
        return vakkenLijst;
    }
    
    public ObservableList<Vak> geefVakken() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(vakkenLijst));
    }

    public List<Groepsbewerking> getGroepsbewerkingenList() {
        if (groepsbewerkingenLijst == null) {
            groepsbewerkingenLijst = ob.geefGroepsbewerkingenJPA();
        }
        return groepsbewerkingenLijst;
    }
    public ObservableList<Groepsbewerking> geefGroepsbewerkingen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(getGroepsbewerkingenList()));
    }
    public ObservableList<Groepsbewerking> geefGroepsbewerkingenHuidigeOefening() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(huidigeOefening.getGroepsBewerkingen()));
    }
    public ObservableList<Doelstelling> geefDoelstellingenHuidigeOefening() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(huidigeOefening.getDoelstellingen()));
    }
    public List<Doelstelling> getDoelstellingenList() {
        if (doelstellingenLijst == null) {
            doelstellingenLijst = ob.geefDoelstellingenJPA();
        }
        return doelstellingenLijst;
    }
    public ObservableList<Doelstelling> geefDoelstellingen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(getDoelstellingenList()));
    }


    public void setGroepsbewerkingenOefening(ObservableList<Groepsbewerking> selectedItems) {
        huidigeOefening.setGroepsbewerkingen(selectedItems);
        
    }

    public void setDoelstellingenOefening(ObservableList<Doelstelling> selectedItems) {
        huidigeOefening.setDoelstellingen(selectedItems);
    }

    /////////////
    /////////////
    //////////////
    /*public void voegNieuweOefeningToe(String naam, String antwoord, File opgave, File feedback, Vak vak,
            List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstelling) {
        if ( de naam van deze oefening al in gebruik is dan) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam");
        }
        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, groepsbewerkingen, doelstelling);
        getOefeningList().add(oefening);
        ob.addOefening(oefening);
    }*/


    public void bewerkOefening(String naam, IOefening oefeningOud, Vak vak, File opgave, List<Groepsbewerking> groepsbewerkingen, String antwoord, File feedback, List<Doelstelling> doelstelling) {

        /*if (ob.getOefRepo().getOefeningByName(naam) != null) {
            throw new IllegalArgumentException("Er bestaan al oefeningen met deze naam");
        }*/
        if (groepsbewerkingen != null && groepsbewerkingen.isEmpty()) {
            throw new IllegalArgumentException("Je moet bewerkingen geven");
        }
        if (doelstelling != null && doelstelling.isEmpty()) {
            throw new IllegalArgumentException("Je moet doelsteliingen geven");
        }
        Oefening oefening = null;
        for(int i = 0; i < oefeningLijst.size(); i++){
            if(oefeningOud == oefeningLijst.get(i)){
                 oefening = oefeningLijst.get(i);
            }
        }
      
        oefening.setNaam(naam);
        oefening.setVak(vak);
        oefening.setOpgave(new PDF(feedback, opgave.getName()));
        oefening.setFeedback(new PDF(feedback, feedback.getName()));
        oefening.setAntwoord(antwoord);
        oefening.setDoelstellingen(doelstelling);
        oefening.setGroepsbewerkingen(groepsbewerkingen);
        ob.getOefRepo().update(oefening);
    }
    public void verwijderOef(IOefening oefn) {
        Oefening oefening = oefeningLijst.stream().filter(oef -> oef.getNaam().equals(oefn.getNaam())).findFirst().get();
        oefeningLijst.remove(oefening);
        ob.deleteOefening(oefening);
    }

}
