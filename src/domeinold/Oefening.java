package domeinold;

import domein.Vak;
import domein.PDF;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

public class Oefening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToOne
    private PDF opgave;
    private double antwoord;
    @OneToOne
    private PDF feedback;
    private Vak vak;

    public Oefening() {
    }

    public Oefening(String naam, PDF opgave, double antwoord, PDF feedback, Vak vak) {
        this.naam = naam;
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.feedback = feedback;
        this.vak = vak;
    }

    public String getNaam() {
        return naam;
    }

    public PDF getOpgave() {
        return opgave;
    }

    public double getAntwoord() {
        return antwoord;
    }

    public PDF getFeedback() {
        return feedback;
    }

    public Vak getVak() {
        return vak;
    }
    
    
}