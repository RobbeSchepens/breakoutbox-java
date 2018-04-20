package domeinold;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Groepsbewerking2 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @Enumerated(EnumType.STRING)
    private EnumBewerking bewerking;
    private double getal;

    public Groepsbewerking2() {
    }

    public Groepsbewerking2(EnumBewerking bewerking, double getal) {
        this.bewerking = bewerking;
        this.getal = getal;
    }

    public EnumBewerking getBewerking() {
        return bewerking;
    }

    public double getGetal() {
        return getal;
    }
    
    @Override
    public String toString() {
        switch (bewerking)
        {
            case OPTELLING:
                return "Tel er " + getal + " bij op.";
            case AFTREKKING:
                return "Trek er " + getal + " van af.";
            case VERMENIGVULDIGING:
                return "Vermenigvuldig met " + getal + ".";
            case DELING:
                return "Deel door " + getal + ".";
        }
        return "Groepsbewerking{" + "bewerking=" + bewerking + ", getal=" + getal + '}';
    }
}