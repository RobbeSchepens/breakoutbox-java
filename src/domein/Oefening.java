package domein;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
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
    @NamedQuery(name = "Oefening.findByName", query = "select e from Oefening e where e.naam = :oefeningnaam"),})
public class Oefening implements IOefening, Serializable {

    private static final long serialVersionUID = 1L;

    // De ID is gewijzigd naar een getter getId nadat naam een SimpleProperty moest worden en 
    // @Basic moet zijn om aanspreekbaar te zijn in de named query.
    @Transient
    private Long id;

    @Transient
    private final StringProperty naam = new SimpleStringProperty();

    private String antwoord;

    @OneToOne(cascade = CascadeType.PERSIST)
    private PDF opgave;
    @OneToOne(cascade = CascadeType.PERSIST)
    private PDF feedback;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Vak vak;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Doelstelling> doelstellingen;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Groepsbewerking> groepsbewerkingen;
    
//    @Transient
//    private Set<OefeningObserver> observers;

    public Oefening() {
    }

    public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback,
            List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {
        setGroepsbewerkingen(groepsbewerkingen);
        setVak(vak);
        String pdfName = String.format("%s_%s_%s_%s", "Opgave", naam, getVak(), opgave.getName());
        setOpgave(new PDF(opgave, pdfName));
        setAntwoord(antwoord);
        pdfName = String.format("%s_%s_%s_%s", "Feedback", naam, getVak(), opgave.getName());
        setFeedback(new PDF(feedback, pdfName));
        setNaam(naam);
        setDoelstellingen(doelstellingen);
    }

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    @Basic
    @Access(AccessType.PROPERTY)
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Er moet een naam zijn");
        }
        naam.set(value);
        //notifyObservers();
    }

    @Override
    public StringProperty naamProperty() {
        return naam;
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
        //notifyObservers();
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
        //notifyObservers();
    }

    @Override
    public List<Groepsbewerking> getGroepsBewerkingen() {
        return groepsbewerkingen;
    }

    public void setGroepsbewerkingen(List<Groepsbewerking> groepsbewerkingen) {
        if (groepsbewerkingen == null) {
            throw new IllegalArgumentException("je moet bewerkingen geven");
        }
        this.groepsbewerkingen = groepsbewerkingen;
    }

    @Override
    public List<Doelstelling> getDoelstellingen() {
        return doelstellingen;
    }

    public void setDoelstellingen(List<Doelstelling> doelstellingen) {
        this.doelstellingen = doelstellingen;
    }

    @Override
    public String toString() {
        return getNaam();
    }

//    @Override
//    public void addObserver(OefeningObserver o) {
//        if (!observers.contains(o))
//            observers.add(o);
//    }
//
//    @Override
//    public void removeObserver(OefeningObserver o) {
//        observers.remove(o);
//    }
//
//    public void removeAllObservers() {
//        observers.clear();
//    }
//
//    public void notifyObservers() {
//        observers.forEach((observer) -> {
//            observer.update(getNaam(), antwoord, vak, groepsbewerkingen, doelstellingen);
//        });
//    }
//
//    public void setHuidig() {
//        notifyObservers();
//    }
}
