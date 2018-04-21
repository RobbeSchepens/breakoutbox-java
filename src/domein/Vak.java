package domein;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
public class Vak implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Transient
    private final StringProperty naam = new SimpleStringProperty();

    public Vak() {
    }

    public Vak(String naam) {
        setNaam(naam);
    }
    
    @Access(AccessType.PROPERTY)
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String value) {
        naam.set(value);
    }

    public StringProperty naamProperty() {
        return naam;
    }

    @Override
    public String toString() {
        return getNaam().substring(0, 1).toUpperCase() + getNaam().substring(1);
    }
}
