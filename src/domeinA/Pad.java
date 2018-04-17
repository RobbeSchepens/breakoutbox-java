package domeinA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "paden")
public class Pad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int padId;
    private Set<Opdracht> opdrachten;

    protected Pad() {
    }

    public void addOpdracht(Opdracht o) {
        opdrachten.add(o);
    }
}