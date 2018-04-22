package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Groepsbewerking<T> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String omschrijving;
    private T teBewerken;

    // Op deze klasse Strategy Pattern toepassen en 
    // krijgt dan niet-generieke subklassen voor de bewerking concreet te maken voor double/string
    // zoals: Rotate, Switch, Add, Multiply
    public Groepsbewerking() {
    }

    public Groepsbewerking(String omschrijving, T teBewerken) {
        this.omschrijving = omschrijving;
        this.teBewerken = teBewerken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public T getTeBewerken() {
        return teBewerken;
    }

    public void setTeBewerken(T teBewerken) {
        this.teBewerken = teBewerken;
    }

    @Override
    public abstract String toString();

    public abstract T berekenOplossing(T teBewerken);

}
