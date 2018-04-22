package domein;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
@NamedQueries({ 
    @NamedQuery(name = "Oefening.findByName", query = "select e from Oefening e where e._naam = :oefeningnaam")
})
public class Oefening implements IOefening, Serializable, Subject {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private final StringProperty _naam = new SimpleStringProperty();
    private String naam;

    @OneToOne
    private PDF opgave;
    private String antwoord;

    @OneToOne
    private PDF feedback;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Vak vak;

    private SimpleObjectProperty<List<String>> doelstellingen = new SimpleObjectProperty();

    @ManyToMany
    private Set<Groepsbewerking> groepsbewerkingen = new HashSet<>();
    @Transient
    private Set<OefeningObserver> observers = new HashSet<>();

    public Oefening() {
    }

    public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback, Set<Groepsbewerking> groepsbewerkingen, List<String> doelstellingen) {
        if (opgave == null) {
            throw new IllegalArgumentException("er moeten een opgave zijn");
        }
        if (feedback == null) {
            throw new IllegalArgumentException("er moet feedback zijn");
        }

        setGroepsbewerkingen(groepsbewerkingen);
        setVak(vak);
        String pdfName = String.format("%s_%s_%s", "Opgave", naam, opgave.getName());
        setOpgave(new PDF(opgave, pdfName));
        setAntwoord(antwoord);
        pdfName = String.format("%s_%s_%s", "Feedback", naam, opgave.getName());
        setFeedback(new PDF(feedback, pdfName));
        setNaam(naam);
        setDoelstelling(doelstellingen);

    }

    @Override
    //@Access(AccessType.PROPERTY)
    public String getNaam() {
        return _naam.get();
    }

    public void setNaam(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Er moet een naam zijn");
        }
        _naam.set(value);
        naam = value;
        notifyObservers();
    }

    @Override
    public StringProperty naamProperty() {
        return _naam;
    }

    public ObjectProperty<List<String>> doelstellingenProperty() {
        return doelstellingen;
    }

    @Override
    public PDF getOpgave() {
        return opgave;
    }

    public void setOpgave(PDF opgave) {
        if (opgave == null) {
            throw new IllegalArgumentException("Er moet een opgave zijn");
        }
        this.opgave = opgave;
    }

    @Override
    public String getAntwoord() {

        return antwoord;
    }

    public void setAntwoord(String antwoord) {
        if (antwoord == null) {
            throw new IllegalArgumentException("Er moet een antwoord zijn");
        }
        this.antwoord = antwoord;
        notifyObservers();
    }

    @Override
    public PDF getFeedback() {
        return feedback;
    }

    public void setFeedback(PDF feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Er moet feedback zijn");
        }
        this.feedback = feedback;
    }

    @Override
    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        if (vak == null) {
            throw new IllegalArgumentException("er moet een vak geselcteerd zijn");
        }
        this.vak = vak;
        notifyObservers();
    }

    public Set<Groepsbewerking> getGroepsbewerkingen() {
        return groepsbewerkingen;
    }

    public void setGroepsbewerkingen(Set<Groepsbewerking> groepsbewerkingen) {
        if (groepsbewerkingen == null) {
            throw new IllegalArgumentException("er moeten groepsbewerkingen zijn");
        }
        this.groepsbewerkingen = groepsbewerkingen;
    }

    public List<String> getDoelstellingen() {
        return this.doelstellingen.get();
    }

    public void setDoelstelling(List<String> doelstellingen) {
        if (doelstellingen == null) {
            throw new IllegalArgumentException("er moeten doelstellingen zijn");
        }
        this.doelstellingen.set(doelstellingen);
    }

    @Override
    public void addObserver(OefeningObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(OefeningObserver o) {
        observers.remove(o);
    }

    private void notifyObservers() {
        for (OefeningObserver observer : observers) {
            observer.update(getNaam(), antwoord, vak);
        }
    }

    public void setHuidig() {
        notifyObservers();
    }

}
