package domein;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Groep implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "sessieId")
    private Sessie sessie;
    //@Basic(fetch=LAZY)
    @OneToMany
    private Set<Leerling> leerlingen;
    //@Basic(fetch=LAZY)
    @OneToOne
    private Pad padd;

    public Groep() {
    }

    public Groep(Set<Leerling> leerlingen) {
        //@TODO
        // Domeinregels voor leerlingen in groep te zetten (min 2, max 4)
        // In setter!
        this.leerlingen = leerlingen;
    }

    public Set<Leerling> getLeerlingen() {
        return this.leerlingen;
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

    public Pad getPad() {
        return padd;
    }

    public void setPad(Pad pad) {
        this.padd = pad;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.leerlingen);
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
        final Groep other = (Groep) obj;
        if (!Objects.equals(this.leerlingen, other.leerlingen)) {
            return false;
        }
        return true;
    }
}