package domein;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OefeningNull implements IOefening {

    private final StringProperty naam = new SimpleStringProperty();
    private String antwoord;
    private Vak vak;
    private PDF opgave;
    private PDF feedback;
    private List<Doelstelling> doelstellingen;
    private List<Groepsbewerking> groepsbewerkingen;

    public OefeningNull() {
        setNaam("");
        antwoord = "";
        vak = null;
        opgave = null;
        feedback = null;
        doelstellingen = null;
        groepsbewerkingen = null;
    }
    
    @Override
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String value) {
        naam.set(value);
    }

    @Override
    public StringProperty naamProperty() {
        return naam;
    }

    @Override
    public String getAntwoord() {
        return antwoord;
    }

    @Override
    public PDF getOpgave() {
        return opgave;
    }

    @Override
    public PDF getFeedback() {
        return feedback;
    }

    @Override
    public Vak getVak() {
        return vak;
    }

    @Override
    public List<Groepsbewerking> getGroepsBewerkingen() {
        return groepsbewerkingen;
    }

    @Override
    public List<Doelstelling> getDoelstellingen() {
        return doelstellingen;
    }
}
