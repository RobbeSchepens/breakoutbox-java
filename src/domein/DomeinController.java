package domein;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import repository.GenericDaoJpa;

public class DomeinController {

    // Oefening
    private Oefening huidigeOefening;
    private OefeningBeheerder ob = new OefeningBeheerder();
    private List<Oefening> oefeningLijst;
    private List<Vak> vakkenLijst;
    private List<Groepsbewerking> groepsbewerkingenLijst;
    private List<Doelstelling> doelstellingenLijst;
    private FilteredList<Oefening> filteredOefeningList;

    // Box
    //private Box huidigeBox;
    // Sessie
    //private Sessie huidigeSessie;
    
    public DomeinController() {
        this.oefeningLijst = ob.geefOefeningenJPA();
        this.vakkenLijst = ob.geefVakkenJPA();
        this.groepsbewerkingenLijst = ob.geefGroepsbewerkingenJPA();
        this.doelstellingenLijst = ob.geefDoelstellingenJPA();

        sortAllLists();
        laadOefeningen();
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
        }
        return oefeningLijst;
    }

    public IOefening getHuidigeOefening() {
        return huidigeOefening;
    }

    public boolean noOefeningen() {
        return oefeningLijst.isEmpty();
    }

    public int geefAantalOefeningen() {
        return oefeningLijst.size();
    }

    public List<String> geefOefeningNaamLijst() {
        return getOefeningList().stream().map(Oefening::getNaam).collect(Collectors.toList());
    }

    public ObservableList<IOefening> geefOefeningen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(oefeningLijst));
    }

    // Deze dient om aan GUI te geven. 
    // Voor gebruik in andere domeinmethodes, gebruik: ob.geefOefeningByNaamJpa(naam);
    public IOefening geefOefeningByNaam(String naam) {
        return ob.geefOefeningByNaamJpa(naam);
    }

    public void setHuidigeOefening(IOefening huidigeOefening) {
        this.huidigeOefening = (Oefening) huidigeOefening;
        //this.huidigeOefening.setHuidig();
    }

    public void addOefeningObserver(OefeningObserver o) {
        //huidigeOefening.addObserver(o);
    }

    public void changeFilter(String filterValue) {
        filteredOefeningList.setPredicate(oefening -> {
            // If filter text is empty, display all persons.
            if (filterValue == null || filterValue.isEmpty()) {;
                return true;
            }
            // Compare first name and last name of every person with   
            //filter text.
            String lowerCaseValue = filterValue.toLowerCase();
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue);
        });
    }

    // ================
    // == Vakken ======
    // ================
    public List<Vak> getVakkenList() {
        //return new GenericDaoJpa<>(Vak.class).findAll();

        if (vakkenLijst == null) {
            vakkenLijst = ob.geefVakkenJPA();
        }
        return vakkenLijst;
    }

    public List<Groepsbewerking> getGroepsbewerkingenLijst() {
        if (groepsbewerkingenLijst == null) {
            groepsbewerkingenLijst = ob.geefGroepsbewerkingenJPA();
        }
        return groepsbewerkingenLijst;
    }

    public List<Doelstelling> getDoelstellingenLijst() {
        if (doelstellingenLijst == null) {
            doelstellingenLijst = ob.geefDoelstellingenJPA();
        }
        return doelstellingenLijst;
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
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(groepsbewerkingenLijst));
    }

    public List<Doelstelling> getDoelstellingenList() {
        if (doelstellingenLijst == null) {
            doelstellingenLijst = ob.geefDoelstellingenJPA();
        }
        return doelstellingenLijst;
    }

    public ObservableList<Doelstelling> geefDoelstellingen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(doelstellingenLijst));
    }

    public void voegNieuweOefeningToe(String naam, Vak vak, File opgave, List<Groepsbewerking> groepsbewerkingen, String antwoord, File feedback, List<Doelstelling> doelstelling) {
        /* if ( de naam van deze oefening al in gebruik is dan) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam");
        }*/
        if (groepsbewerkingen != null && groepsbewerkingen.isEmpty()) {
            throw new IllegalArgumentException("er moeten bewerkingen zijn");
        }
        if (doelstelling != null && doelstelling.isEmpty()) {
            throw new IllegalArgumentException("er moeten doelstellingen zijn");
        }

        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, groepsbewerkingen, doelstelling);
        oefeningLijst.add(oefening);
        ob.addOefening(oefening);
    }

    public void bewerkOefening(String naam, Vak vak, File opgave, List<Groepsbewerking> groepsbewerkingen, String antwoord, File feedback, List<Doelstelling> doelstelling) {
        if (ob.getOefRepo().exists(naam)) {
            throw new IllegalArgumentException("Er bestaan al oefeningen met deze naam");
        }
        if (groepsbewerkingen != null && groepsbewerkingen.isEmpty()) {
            throw new IllegalArgumentException("Je moet bewerkingen geven");
        }
        if (doelstelling != null && doelstelling.isEmpty()) {
            throw new IllegalArgumentException("Je moet doelsteliingen geven");
        }
        System.out.println("1");
        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, groepsbewerkingen, doelstelling);
        System.out.println("2");
        oefeningLijst.add(oefening);
        System.out.println("3");
        ob.getOefRepo().insert(oefening);
        System.out.println("4");
        laadOefeningen();

    }

    public void verwijderOef(String naam) {
        Oefening oefn = filteredOefeningList.stream().filter(oef -> oef.getNaam().equals(naam)).findFirst().get();
        filteredOefeningList.remove(oefn);
        ob.getOefRepo().delete(oefn);
        laadOefeningen();
    }

    public void laadOefeningen() {
        oefeningLijst = ob.getOefRepo().findAll();

    }

    public <T> void verwijderObject(T object) {

    }

}