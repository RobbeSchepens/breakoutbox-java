package domein;

import java.io.Serializable;
import java.util.HashSet;
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
    @NamedQuery(name = "Leerkracht.findByName", 
            query = "select lk from Leerkracht lk where lk.voornaam = :voornaam and lk.achternaam = :achternaam"),
    @NamedQuery(name = "Leerkracht.findByEmail", 
            query = "select lk from Leerkracht lk where lk.email = :email")
})
public class Leerkracht implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String voornaam;
    private String achternaam;
    private String email;
    @OneToMany
    private Set<Sessie> sessies = new HashSet<>();
    @OneToMany
    private Set<Klas> klassen = new HashSet<>();

    public Leerkracht() {
    }

    public Leerkracht(String voornaam, String achternaam, String email) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }

    public void addSessie(Sessie e) {
        sessies.add(e);
    }

    public void removeSessie(Sessie e) {
        sessies.remove(e);
    }

    public void addKlas(Klas e) {
        klassen.add(e);
    }

    public void removeKlas(Klas e) {
        klassen.remove(e);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.voornaam);
        hash = 97 * hash + Objects.hashCode(this.achternaam);
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
        final Leerkracht other = (Leerkracht) obj;
        if (!Objects.equals(this.voornaam, other.voornaam)) {
            return false;
        }
        if (!Objects.equals(this.achternaam, other.achternaam)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return voornaam + " " + achternaam;
    }
}