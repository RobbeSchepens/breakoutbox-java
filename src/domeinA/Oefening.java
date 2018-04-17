package domeinA;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "oefeningen")
public class Oefening implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oefeningId;

    private String naam;
    private Opgave opgave;
    private String antwoord;
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
}