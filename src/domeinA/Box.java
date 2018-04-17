package domeinA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "boxen")
public class Box implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boxId;

    private String naam;
    private String omschrijving;
    private Set<Actie> acties;
    private Set<Oefening> oefeningen;
    private Set<Toegangscode> toegangscodes;

    protected Box() {
    }

    public Box(Set<Actie> acties, Set<Oefening> oefeningen, Set<Toegangscode> toegangscodes, String omschrijving, String naam) {
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.acties = acties;
        this.oefeningen = oefeningen;
        this.toegangscodes = toegangscodes;
    }
}