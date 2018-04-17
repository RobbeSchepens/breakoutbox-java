package domeinA;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "vakken")
public class Vak implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int vakId;

    public String naam;
    public String omschrijving;

    protected Vak() {
    }

    public Vak(String naam, String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }
}