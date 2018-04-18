package domein;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Box.findById", query = "select e from Box e where e.id = :id"),
    @NamedQuery(name = "Box.findByName", query = "select e from Box e where e.naam = :boxnaam")
})
public class Box implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private String omschrijving;
    @OneToMany
    private Set<Actie> acties;
    @OneToMany
    private Set<Oefening> oefeningen;
    @OneToMany
    private Set<Toegangscode> toegangscodes;
    @OneToMany
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