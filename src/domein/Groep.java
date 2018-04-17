package domein;

import domein.Leerling;
import domeinA.Pad;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "groepen")
public class Groep implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groepId;

    private Set<Leerling> leerlingen;
    private Pad pad;

    protected Groep() {
    }

    public Groep(Pad pad, Set<Leerling> leerlingen) {
        this.pad = pad;
        this.leerlingen = leerlingen;
    }

    public void setLeerlingen(Set<Leerling> leerlingen) {
        this.leerlingen = leerlingen;
    }

    public void addLeerling(Leerling l) {
        leerlingen.add(l);
    }

    public Set<Leerling> getLeerlingen() {
        return leerlingen;
    }
}