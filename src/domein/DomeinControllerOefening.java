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

public class DomeinControllerOefening extends Observable {

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
    
    public DomeinControllerOefening() {
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
            if (filterValue == null || filterValue.isEmpty() || filterValue.equals("Alle vakken")) {
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
        filteredOefeningList = new FilteredList<>(geefOefeningen(), p -> true); // Temp fix for tbv not resetting
        setChanged();
        notifyObservers();
    }

    public void voegNieuweOefeningToe(String naam, String antwoord, File opgave, File feedback, Vak vak) {
        if (ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }
        System.out.println("lists nieuwe oef");
        System.out.println(listGroepsbewerkingenVanOefening);
        Oefening oefening = new Oefening(naam, antwoord, vak, opgave, feedback, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        getOefeningList().add(oefening);
        ob.addOefening(oefening);
        filteredOefeningList = new FilteredList<>(geefOefeningen(), p -> true); // Temp fix for tbv not resetting
        setChanged();
        notifyObservers();
    }

    public void pasOefeningAan(String naam, String antwoord, File opgave, File feedback, Vak vak) {
        if (!huidigeOefening.getNaam().equals(naam) && ob.geefOefeningByNaamJpa(naam) != null) {
            throw new IllegalArgumentException("Er bestaat al een oefening met deze naam.");
        }
        if (listDoelstellingenVanOefening.isEmpty()) {
            listDoelstellingenVanOefening = huidigeOefening.getDoelstellingen();
        }
        if (listGroepsbewerkingenVanOefening.isEmpty()) {
            listGroepsbewerkingenVanOefening = huidigeOefening.getGroepsBewerkingen();
        }
        huidigeOefening.roepSettersAan(naam, antwoord, vak, opgave, feedback, listGroepsbewerkingenVanOefening, listDoelstellingenVanOefening);
        ob.updateOefening(huidigeOefening);

        setChanged();
        notifyObservers();
    }
    
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
        groepsbewerkingenLijst = ob.geefGroepsbewerkingenJPA(); // niet checken op null geeft fouten
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
        doelstellingenLijst = ob.geefDoelstellingenJPA();// niet checken op null geeft fouten
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
}