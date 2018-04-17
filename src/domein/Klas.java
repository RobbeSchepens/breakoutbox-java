package domein;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Klas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int klasId;
    @ManyToOne
    private Leerkracht leerkracht;
    @OneToMany
    private Set<Leerling> leerlingen = new HashSet<>();

    public Klas() {
    }

    public Klas(Leerkracht leerkracht) {
        this.leerkracht = leerkracht;
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
}