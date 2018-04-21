package domein;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
@NamedQueries({
    @NamedQuery(name = "Oefening.findByName", query = "select e from Oefening e where e.naam = :oefeningnaam")
})
public class Oefening implements IOefening, Serializable, Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToOne
    private PDF opgave;
    private String antwoord;
    @OneToOne
    private PDF feedback;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Vak vak;
    //private Set<String> doelstellingen = new HashSet<>();
    @ManyToMany
    private Set<Groepsbewerking> groepsbewerkingen = new HashSet<>();
    @Transient
    private Set<OefeningObserver> observers = new HashSet<>();
    
    public Oefening() { 
    }

    public Oefening(String naam, String antwoord, Vak vak) {
        this.naam = naam;
        this.antwoord = antwoord;
        this.vak = vak;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
        notifyObservers();
    }

    @Override
    public PDF getOpgave() {
        return opgave;
    }

    public void setOpgave(PDF opgave) {
        this.opgave = opgave;
    }

    @Override
    public String getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
        notifyObservers();
    }

    @Override
    public PDF getFeedback() {
        return feedback;
    }

    public void setFeedback(PDF feedback) {
        this.feedback = feedback;
    }

    @Override
    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        this.vak = vak;
        notifyObservers();
    }

    @Override
    public void addObserver(OefeningObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeObserver(OefeningObserver o) {
        observers.remove(o);
    }
    
    private void notifyObservers() {
        for (OefeningObserver observer : observers) {
            observer.update(naam, antwoord, vak);
        }
    }

    public void setHuidig() {
        notifyObservers();
    }
}