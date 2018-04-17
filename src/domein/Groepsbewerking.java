package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Groepsbewerking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groepsbewerkingId;
    @ManyToOne
    private EnumBewerking bewerking;
    private double getal;

    public Groepsbewerking() {
    }

    public Groepsbewerking(EnumBewerking bewerking, double getal) {
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