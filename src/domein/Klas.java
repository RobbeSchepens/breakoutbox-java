package domein;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table(name = "klassen")
public class Klas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int klasId;
    @OneToMany
    private Set<Leerling> leerlingen;
    @ManyToOne
    private Leerkracht leerkracht;

    protected Klas() {
    }

    public Klas(Leerkracht leerkracht, Set<Leerling> leerlingen) {
        this.leerkracht = leerkracht;
        this.leerlingen = leerlingen;
    }
}