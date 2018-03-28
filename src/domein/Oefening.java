/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * @author Daan
 */
@Entity
@Table(name = "oefeningen")
public class Oefening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oefeningId;

    private String naam;
    private Opgave opgave;
    private String antwoord;
    private GroepsBewerking groepsbewerking;
    private Feedback feedback;
    private Vak vak;

    protected Oefening() {

    }

    public Oefening(String naam, Opgave opgave, String antwoord, Feedback feedback, Vak vak) {
        this.naam = naam;
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.feedback = feedback;
        this.vak = vak;
    }

    public GroepsBewerking getGroepsbewerking() {
        return groepsbewerking;
    }

    public void setGroepsbewerking(GroepsBewerking groepsbewerking) {
        this.groepsbewerking = groepsbewerking;
    }

}
