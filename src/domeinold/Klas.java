package domeinold;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Klas.findById", query = "select e from Klas e where e.id = :id")
})
public class Klas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "leerkrachtId")
    private Leerkracht leerkracht;
    //@Basic(fetch=LAZY)
    @OneToMany(mappedBy = "klas")
    private Set<Leerling> leerlingen = new HashSet<>();
    @OneToMany(mappedBy = "klas")
    private Set<Sessie> sessies = new HashSet<>();

    public Klas() {
    }

    public Klas(Leerkracht leerkracht) {
        this.leerkracht = leerkracht;
    }

    public Set<Leerling> getLeerlingen() {
        return leerlingen;
    }
    
    public void setLeerlingen(Set<Leerling> leerlingen) {
        this.leerlingen = leerlingen;
    }
    
    public void addLeerling(Leerling e) {
        leerlingen.add(e);
    }

    public void removeLeerling(Leerling e) {
        leerlingen.remove(e);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.leerkracht);
        hash = 97 * hash + Objects.hashCode(this.leerlingen);
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
        final Klas other = (Klas) obj;
        if (!Objects.equals(this.leerkracht, other.leerkracht)) {
            return false;
        }
        if (!Objects.equals(this.leerlingen, other.leerlingen)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Klas met id " + id + " van leerkracht " + leerkracht.toString() + ".";
    }
}