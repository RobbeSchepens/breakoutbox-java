package domein;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DomeinController {
    // Oefening
    private OefeningBeheerder ob = new OefeningBeheerder();
    private List<Oefening> oefeningLijst;
    private List<Vak> vakkenLijst;
    private Oefening huidigeOefening;
    private FilteredList<Oefening> filteredOefeningList;
    
    // Box
    //private Box huidigeBox;

    // Sessie
    //private Sessie huidigeSessie;

    public DomeinController() {
        this.oefeningLijst = ob.geefOefeningenJPA();
        this.vakkenLijst = ob.geefVakkenJPA();
    }
    
    // ================
    // == Oefeningen ==
    // ================
    private List<Oefening> getOefeningList() {
        if (oefeningLijst == null) oefeningLijst = ob.geefOefeningenJPA();
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
    
    public List<String> geefOefeningNaamLijst(){
        return getOefeningList().stream().map(Oefening::getNaam).collect(Collectors.toList());
    }
    
    public ObservableList<IOefening> geefOefeningen(){
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(oefeningLijst));
    }
    
    public ObservableList<Oefening> geefOefeningen1(){
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(oefeningLijst));
    }

    public void setHuidigeOefening(IOefening huidigeOefening) {
        this.huidigeOefening = (Oefening)huidigeOefening;
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
    
    public void voegNieuweOefeningToe(String naam, String antwoord, Vak vak) {
        Oefening oef = new Oefening(naam, antwoord, vak);
        oefeningLijst.add(oef);
        ob.addOefening(oef);
    }
    
    // ================
    // == Vakken ======
    // ================
    public List<Vak> getVakkenList() {
        if (vakkenLijst == null) vakkenLijst = ob.geefVakkenJPA();
        return vakkenLijst;
    }
    
    public ObservableList<Vak> geefVakken(){
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(vakkenLijst));
    }
    
    
    public <T> void verwijderObject(T object){
        
    }
    
}