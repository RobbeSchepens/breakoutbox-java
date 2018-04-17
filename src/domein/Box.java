package domein;

import domein.Oefening;
import domein.Toegangscode;
import domein.Actie;
import domein.Groepsbewerking;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Box implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boxId;
    private String naam;
    private String omschrijving;
    private Set<Actie> acties;
    private Set<Oefening> oefeningen;
    private Set<Toegangscode> toegangscodes;
    private Set<Groepsbewerking> groepsbewerkingen;

    public Box() {
    }

    public Box(String naam, String omschrijving, Set<Actie> acties, Set<Oefening> oefeningen, Set<Toegangscode> toegangscodes, Set<Groepsbewerking> groepsbewerkingen) {
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.acties = acties;
        this.oefeningen = oefeningen;
        this.toegangscodes = toegangscodes;
        this.groepsbewerkingen = groepsbewerkingen;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.naam);
        hash = 97 * hash + Objects.hashCode(this.omschrijving);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Box other = (Box) obj;
        if (!Objects.equals(this.naam, other.naam)) {
            return false;
        }
        if (!Objects.equals(this.omschrijving, other.omschrijving)) {
            return false;
        }
        return true;
    }
}