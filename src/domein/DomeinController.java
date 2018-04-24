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
    private List<Vak> vakkenLijst;

    private List<Groepsbewerking> groepsbewerkingenLijst;

    private List<Doelstelling> doelstellingenLijst;
    private FilteredList<Oefening> filteredOefeningList;

    public DomeinController() {
        this.vakkenLijst = ob.geefVakkenJPA();
        this.groepsbewerkingenLijst = ob.geefGroepsbewerkingenJPA();
        this.doelstellingenLijst = ob.geefDoelstellingenJPA();

        sortAllLists();
        //laadOefeningen();
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

    public IOefening geefOefeningByNaam(String naam) {
        return ob.geefOefeningByNaamJpa(naam);
    }

    public void setHuidigeOefening(IOefening huidigeOefening) {
//        if (this.huidigeOefening != null)
//            this.huidigeOefening.removeAllObservers();
        this.huidigeOefening = (Oefening) huidigeOefening;
//        setChanged();
//        notifyObservers(this.huidigeOefening);
        //this.huidigeOefening.setHuidig();
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

    // ================
    // == Vakken ======
    // ================
    public List<Vak> getVakkenList() {
        if (vakkenLijst == null) {
            vakkenLijst = ob.geefVakkenJPA();
        }
        return vakkenLijst;
    }

    /*public List<Doelstelling> getDoelstellingenLijst() {
        if (doelstellingenLijst == null)
            doelstellingenLijst = ob.geefDoelstellingenJPA();
        return doelstellingenLijst;
    }*/
    public ObservableList<Vak> geefVakken() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(vakkenLijst));
    }

    //////////////////////// doelstellingen en bewerkingen lists //////////////////////////////
    public List<Groepsbewerking> geefAlleBewerkingen() {
        return ob.geefGroepsbewerkingenJPA();
    }

    public List<Doelstelling> geefAlleDoelstellingen() {
        return ob.geefDoelstellingenJPA();
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

    public void bewerkOefening(String naam, Vak vak, File opgave, List<Groepsbewerking> groepsbewerkingen, String antwoord, File feedback, List<Doelstelling> doelstelling) {
        if (ob.getOefRepo().getOefeningByName(naam) != null) {
            throw new IllegalArgumentException("Er bestaan al oefeningen met deze naam");
        }
        if (groepsbewerkingen != null && groepsbewerkingen.isEmpty()) {
            throw new IllegalArgumentException("Je moet bewerkingen geven");
        }
        if (doelstelling != null && doelstelling.isEmpty()) {
            throw new IllegalArgumentException("Je moet doelsteliingen geven");
        }
        System.out.println(naam + " " + antwoord + " " + vak);
        System.out.println(groepsbewerkingen);
        System.out.println(doelstelling);
        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, groepsbewerkingen, doelstelling);

        getOefeningList().add(oefening);

        ob.getOefRepo().insert(oefening);

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

    public void verwijderOefening(IOefening o) {
        ob.deleteOefening((Oefening) o);
        oefeningLijst.remove((Oefening) o);
        setChanged();
        notifyObservers(oefeningLijst);
    }

}
