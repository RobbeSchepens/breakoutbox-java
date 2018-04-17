package domein;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Sessie.findByCode", query = "select s from Sessie s where s.code = :code"),
    @NamedQuery(name = "Sessie.findByName", query = "select s from Sessie s where s.naam = :sessienaam")
})
public class Sessie implements Serializable {
    private static long serialVersionUID = 1L;
    @Id
    private String code;
    private String naam;
    private String omschrijving;
    @OneToMany
    private Set<Groep> groepen = new HashSet<>();
    @ManyToOne
    private Klas klas;

    public Sessie() {
    }

    public Sessie(String code, String naam, String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }
    
    public String getCode() {
        return code;
    }

    public Set<Groep> getGroepSet() {
        return Collections.unmodifiableSet(groepen);
    }
    
    public void addGroep(Groep groep){
        groepen.add(groep);
    }
    
    public void removeGroep(Groep groep){
        groepen.remove(groep);
    }

    public Klas getKlas() {
        return klas;
    }

    public void setKlas(Klas klas) {
        this.klas = klas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.code);
        hash = 61 * hash + Objects.hashCode(this.naam);
        hash = 61 * hash + Objects.hashCode(this.omschrijving);
        hash = 61 * hash + Objects.hashCode(this.klas);
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
        final Sessie other = (Sessie) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.naam, other.naam)) {
            return false;
        }
        if (!Objects.equals(this.omschrijving, other.omschrijving)) {
            return false;
        }
        return true;
    }
}
