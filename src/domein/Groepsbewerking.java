package domein;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

@Entity
@Table(name = "Groepsbewerkingen")
@Inheritance
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
public abstract class Groepsbewerking<T> implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column
    private String omschrijving;
    @Column
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
