package domein;

import java.io.File;
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

    public void voegNieuweOefeningToe(String naam, String antwoord, Vak vak,
            File opgave, String opgaveNaam,
            File feedback, String feedbackNaam,
            List<Groepsbewerking> groepsbewerkingen,
            List<Doelstelling> doelstellingen) {
        
        if (ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }

        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, opgaveNaam,
                feedback, feedbackNaam, groepsbewerkingen, doelstellingen);
        getOefeningList().add(oefening);
        ob.addOefening(oefening);
        setChanged();
        notifyObservers();
    }

    public void pasOefeningAan(String naam, String antwoord, Vak vak,
            File opgave, String opgaveNaam,
            File feedback, String feedbackNaam,
            List<Groepsbewerking> groepsbewerkingen,
            List<Doelstelling> doelstellingen) {
        if (!huidigeOefening.getNaam().equals(naam) && ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }
        groepsbewerkingen = huidigeOefening.getGroepsBewerkingen();
        doelstellingen =  huidigeOefening.getDoelstellingen();
        huidigeOefening.roepSettersAan(naam, antwoord, vak, opgave, opgaveNaam,
                feedback, feedbackNaam, groepsbewerkingen, doelstellingen);
        ob.updateOefening(huidigeOefening);
        setChanged();
        notifyObservers();
    }

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

    public List<Doelstelling> getDoelstellingenList() {
        if (doelstellingenLijst == null) {
            doelstellingenLijst = ob.geefDoelstellingenJPA();
        }
        return doelstellingenLijst;
    }
    
    public ObservableList<Doelstelling> geefDoelstellingen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(getDoelstellingenList()));
    }

    ////////////////////////////////////////////
    public void voegNieuweOefeningToe(String naam, String antwoord, File opgave, File feedback, Vak vak,
            List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstelling) {
        /* if ( de naam van deze oefening al in gebruik is dan) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam");
        }*/
        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, groepsbewerkingen, doelstelling);
        getOefeningList().add(oefening);
        ob.addOefening(oefening);
    }

    public void bewerkOefening(String naam, IOefening oefeningOud, Vak vak, File opgave, List<Groepsbewerking> groepsbewerkingen, String antwoord, File feedback, List<Doelstelling> doelstelling) {
        System.out.println("hiiiii");
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
        oefening.setOpgave(new PDF(feedback, naam));
        oefening.setFeedback(new PDF(feedback, naam));
        oefening.setAntwoord(antwoord);
        oefening.setDoelstellingen(doelstelling);
        oefening.setGroepsbewerkingen(groepsbewerkingen);
        ob.getOefRepo().update(oefening);
    }

    public void verwijderOef(IOefening oefn) {
        Oefening oefening = oefeningLijst.stream().filter(oef -> oef.getNaam().equals(oefn.getNaam())).findFirst().get();
        System.out.println(oefening);
        System.out.println(oefeningLijst);
        oefeningLijst.remove(oefening);
        System.out.println("oef lijst after delete");
        System.out.println(oefeningLijst);
        ob.deleteOefening(oefening);
    }

}