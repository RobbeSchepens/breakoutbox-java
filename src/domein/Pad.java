package domein;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Pad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int padId;
    @OneToMany
    private Set<Opdracht> opdrachten = new HashSet<>();

    public Pad() {
    }

    public void addOpdracht(Opdracht e) {
        opdrachten.add(e);
    }

    public void removeOpdracht(Opdracht e) {
        opdrachten.add(e);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.opdrachten);
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
        final Pad other = (Pad) obj;
        if (!Objects.equals(this.opdrachten, other.opdrachten)) {
            return false;
        }
        return true;
    }
}