package domein;

import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Vak(String naam) {
        setNaam(naam);
    }
    
    @Access(AccessType.PROPERTY)
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String value) {
        controleerNaam(value);
        naam.set(value);
    }
    
    private void controleerNaam(String naam) {
        if(naam.length() < 1)
            throw new NaamTeKortException("Het vak moet minstens 3 tekens lang zijn!");
        if(naam.length() > 40)
            throw new NaamTeLangException("Het vak mag maximum 40 karakters bevatten!");
        
        // Deze karakters mogen, alle andere niet. 
        Pattern p = Pattern.compile("[^A-Za-z0-9&@]");
        Matcher m = p.matcher(naam);
         if (m.find())
        throw new SpecialeTekensInNaamException("Geen speciale tekens toegelaten in de naam van het vak. Enkel A tot Z, 0 tot 9, & of @.");
    }

    public StringProperty naamProperty() {
        return naam;
    }

    @Override
    public String toString() {
        return getNaam().substring(0, 1).toUpperCase() + getNaam().substring(1);
    }
}
