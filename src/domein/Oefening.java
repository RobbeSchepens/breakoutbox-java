package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = "Oefening.findByName", query = "select e from Oefening e where e.naam = :oefeningnaam")
})
public class Oefening implements IOefening, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToOne
    private PDF opgave;
    private String antwoord;
    @OneToOne
    private PDF feedback;
    @ManyToOne
    private Vak vak;
    
    public Oefening() {
    }

    public Oefening(String naam, String antwoord, Vak vak) {
        this.naam = naam;
        this.antwoord = antwoord;
        this.vak = vak;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public PDF getOpgave() {
        return opgave;
    }

    public void setOpgave(PDF opgave) {
        this.opgave = opgave;
    }

    public String getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }

    public PDF getFeedback() {
        return feedback;
    }

    public void setFeedback(PDF feedback) {
        this.feedback = feedback;
    }

    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        this.vak = vak;
    }
}