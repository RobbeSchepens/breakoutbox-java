package domein;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Opdracht implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int opdrachtId;
    private int volgNr;
    private int maxPogingen = 3;
    private int maxTijdInMinuten = 30;
    @OneToOne
    private Actie actie;
    @OneToOne
    private Oefening oefening;
    @OneToOne
    private Toegangscode toegangscode;
    @OneToOne
    private Groepsbewerking groepsbewerking;
    @OneToOne
    private EnumOpdrachtBepaler opdrachtBepaler;

    public Opdracht() {
    }

    public Opdracht(int volgNr, Actie actie, Oefening oefening, Toegangscode toegangscode, Groepsbewerking groepsbewerking, EnumOpdrachtBepaler opdrachtBepaler) {
        this.volgNr = volgNr;
        this.actie = actie;
        this.oefening = oefening;
        this.toegangscode = toegangscode;
        this.groepsbewerking = groepsbewerking;
        this.opdrachtBepaler = opdrachtBepaler;
    }

    public int getVolgNr() {
        return volgNr;
    }

    public void setVolgNr(int volgNr) {
        this.volgNr = volgNr;
    }

    public int getMaxPogingen() {
        return maxPogingen;
    }

    public void setMaxPogingen(int maxPogingen) {
        this.maxPogingen = maxPogingen;
    }

    public int getMaxTijdInMinuten() {
        return maxTijdInMinuten;
    }

    public void setMaxTijdInMinuten(int maxTijdInMinuten) {
        this.maxTijdInMinuten = maxTijdInMinuten;
    }

    public Toegangscode getToegangscode() {
        return toegangscode;
    }

    public void setToegangscode(Toegangscode toegangscode) {
        this.toegangscode = toegangscode;
    }

    public Groepsbewerking getGroepsbewerking() {
        return groepsbewerking;
    }

    public void setGroepsbewerking(Groepsbewerking groepsbewerking) {
        this.groepsbewerking = groepsbewerking;
    }
    
    public double BerekenAntwoord(){
        switch (groepsbewerking.getBewerking())
        {
            case OPTELLING:
                return oefening.getAntwoord() + groepsbewerking.getGetal();
            case AFTREKKING:
                return oefening.getAntwoord() - groepsbewerking.getGetal();
            case VERMENIGVULDIGING:
                return oefening.getAntwoord() * groepsbewerking.getGetal();
            case DELING:
                return oefening.getAntwoord() / groepsbewerking.getGetal();
        }
        throw new IllegalArgumentException("Systeemfout. Antwpord kon niet berekend worden.");
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.actie);
        hash = 47 * hash + Objects.hashCode(this.oefening);
        hash = 47 * hash + Objects.hashCode(this.toegangscode);
        hash = 47 * hash + Objects.hashCode(this.groepsbewerking);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Opdracht other = (Opdracht) obj;
        if (!Objects.equals(this.actie, other.actie)) {
            return false;
        }
        if (!Objects.equals(this.oefening, other.oefening)) {
            return false;
        }
        if (!Objects.equals(this.toegangscode, other.toegangscode)) {
            return false;
        }
        if (!Objects.equals(this.groepsbewerking, other.groepsbewerking)) {
            return false;
        }
        return true;
    }
}