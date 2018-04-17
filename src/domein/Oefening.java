package domein;

import domeinA.Feedback;
import domeinA.Opgave;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Oefening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oefeningId;
    private String naam;
    private String opgave;
    private double antwoord;
    private String feedback;
    private Vak vak;

    public Oefening() {
    }

    public Oefening(String naam, String opgave, double antwoord, String feedback, Vak vak) {
        this.naam = naam;
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.feedback = feedback;
        this.vak = vak;
    }

    public String getNaam() {
        return naam;
    }

    public String getOpgave() {
        return opgave;
    }

    public double getAntwoord() {
        return antwoord;
    }

    public String getFeedback() {
        return feedback;
    }

    public Vak getVak() {
        return vak;
    }
    
    
}