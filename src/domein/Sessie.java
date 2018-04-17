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
import javax.persistence.Table;

@Entity
@Table(name = "sessies")
@NamedQueries({
    @NamedQuery(name = "Sessie.findByCode", query = "select s from sessies s where s.code = :code")
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
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sessie other = (Sessie) obj;
        return Objects.equals(this.code, other.code);
    }
}
