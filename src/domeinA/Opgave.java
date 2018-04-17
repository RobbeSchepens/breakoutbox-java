package domeinA;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "opgaves")
public class Opgave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int opgaveId;

    private String omschrijving;

    public Opgave(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}