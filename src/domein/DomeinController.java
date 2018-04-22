package domein;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.GenericDaoJpa;

public class DomeinController {

    // Oefening
    private OefeningBeheerder ob = new OefeningBeheerder();
    private List<Oefening> oefeningLijst;
    private List<Vak> vakkenLijst;
    private List<Groepsbewerking> groepsbewerkingenLijst;
    private List<Doelstelling> doelstellingenLijst;
    private Oefening huidigeOefening;
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
        this.huidigeOefening.setHuidig();
    }

    public void addOefeningObserver(OefeningObserver o) {
        huidigeOefening.addObserver(o);
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

    public ObservableList<Vak> geefVakken() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(vakkenLijst));
    }

    public List<Groepsbewerking> getGroepsbewerkingenList() {
        if (groepsbewerkingenLijst == null) {
            groepsbewerkingenLijst = ob.geefGroepsbewerkingenJPA();
        }
        return groepsbewerkingenLijst;
    }

    public ObservableList<Groepsbewerking> geefDoelstellingen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(groepsbewerkingenLijst));
    }

    public List<Doelstelling> getDoelstellingenList() {
        if (doelstellingenLijst == null) {
            doelstellingenLijst = ob.geefDoelstellingenJPA();
        }
        return doelstellingenLijst;
    }

    public ObservableList<Doelstelling> geefGroepsbewerkingen() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(doelstellingenLijst));
    }

    public <T> void verwijderObject(T object) {

    }

}
