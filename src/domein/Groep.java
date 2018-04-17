package domein;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Groep implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groepId;
    @OneToMany
    private Set<Leerling> leerlingen;
    private Pad pad;

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
        return pad;
    }

    public void setPad(Pad pad) {
        this.pad = pad;
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