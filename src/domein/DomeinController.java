package domein;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    
    private List<Oefening> getOefeningList() {
        if (oefeningLijst == null) {
            oefeningLijst = ob.geefOefeningenJPA();
        }
        return oefeningLijst;
    }

    public boolean noOefeningen() {
        return oefeningLijst.isEmpty();
    }
    
    public List<String> geefOefeningNaamLijst(){
        return getOefeningList().stream().map(Oefening::getNaam).collect(Collectors.toList());
    }
    
    public ObservableList<IOefening> geefOefeningen(){
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(oefeningLijst));
    }

    public void setHuidigeOefening(Oefening huidigeOefening) {
        this.huidigeOefening = huidigeOefening;
        huidigeOefening.setHuidig();
    }
}