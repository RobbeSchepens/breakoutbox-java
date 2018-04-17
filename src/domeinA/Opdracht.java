package domeinA;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "opdrachten")
public class Opdracht implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OpdrachtId;

    private int volgNr;
    private Actie actie;
    private Oefening oefening;
    private Toegangscode toegangscode;

    protected Opdracht() {
    }

    public Opdracht(int volgNr, Actie actie, Oefening oefening, Toegangscode toegangscode) {
        this.volgNr = volgNr;
        this.actie = actie;
        this.oefening = oefening;
        this.toegangscode = toegangscode;
    }
}