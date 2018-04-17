package domein;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Actie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ActieId;
    private String omschrijving;

    public Actie() {
    }

    public Actie(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.omschrijving);
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
        final Actie other = (Actie) obj;
        return Objects.equals(this.omschrijving, other.omschrijving);
    }
}
