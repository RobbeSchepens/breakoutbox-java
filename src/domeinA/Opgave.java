package domeinA;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Opgave implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int opgaveId;

    private String omschrijving;

    public Opgave() {
    }

    public Opgave(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}