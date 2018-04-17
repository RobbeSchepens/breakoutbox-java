package domeinA;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "acties")
public class Actie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ActieId;
    private String omschrijving;

    public Actie() {
    }

    public Actie(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
