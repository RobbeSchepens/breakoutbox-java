package domein;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DomeinController {
    // Oefening
    private OefeningBeheerder ob = new OefeningBeheerder();
    private List<Oefening> oefeningLijst;
    private Oefening huidigeOefening;
    
    // Box
    //private Box huidigeBox;

    // Sessie
    //private Sessie huidigeSessie;
    
    public List<String> geefOefeningLijst(){
        return getOefeningList().stream().map(Oefening::getNaam).collect(Collectors.toList());
    }
    
    private List<Oefening> getOefeningList() {
        if (oefeningLijst == null) {
            oefeningLijst = ob.geefOefeningenJPA();
        }
        return oefeningLijst;
    }
    
    public ObservableList<IOefening> geefOefeningen(){
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(oefeningLijst));
    }
}