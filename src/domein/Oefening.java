package domein;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = "Oefening.findById", query = "select e from Oefening e where e.id = :id"),
    @NamedQuery(name = "Oefening.findByName", query = "select e from Oefening e where e.naam = :oefeningnaam")
})
public class Oefening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToOne
    private Opgave opgave;
    private double antwoord;
    @OneToOne
    private Feedback feedback;
    private Vak vak;

    public Oefening() {
    }

    public Oefening(String naam, Opgave opgave, double antwoord, Feedback feedback, Vak vak) {
        this.naam = naam;
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.feedback = feedback;
        this.vak = vak;
    }

    public String getNaam() {
        return naam;
    }

    public Opgave getOpgave() {
        return opgave;
    }

    public double getAntwoord() {
        return antwoord;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public Vak getVak() {
        return vak;
    }
    
    
}