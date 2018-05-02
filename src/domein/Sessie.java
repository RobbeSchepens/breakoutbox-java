package domein;

import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Oefening.findByName", query = "select e from Sessie e where e.naam = :sessienaam")
})
public class Sessie implements ISessie, Serializable {

    private static final long serialVersionUID = 1L;

    // De ID is gewijzigd naar een getter getId nadat naam een SimpleProperty moest worden en 
    // @Basic moet zijn om aanspreekbaar te zijn in de named query.
    @Transient
    private Long id;

    @Transient
    private final StringProperty naam = new SimpleStringProperty();
    private String omschrijving;
    private LocalDate startdatum;

    public Sessie() {
    }

    public Sessie(String naam, String omschrijving, LocalDate startdatum) {
        setNaam(naam);
        setOmschrijving(omschrijving);
        setStartdatum(startdatum);
    }

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    @Basic
    @Access(AccessType.PROPERTY)
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String value) {
        controleerNaam(value);
        naam.set(value);
    }

    @Override
    public StringProperty naamProperty() {
        return naam;
    }

    private void controleerNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Er werd geen naam opgegeven.");
        }
        if (naam.length() < 3) {
            throw new NaamTeKortException("Naam moet minstens 3 tekens lang zijn!");
        }
        if (naam.length() > 40) {
            throw new NaamTeLangException("Naam mag maximum 40 karakters bevatten!");
        }

        // Deze karakters mogen, alle andere niet. 
        Pattern p = Pattern.compile("[^A-Za-z0-9._\\-<>+?!=$%&*()| ]");
        Matcher m = p.matcher(naam);
        if (m.find()) {
            throw new SpecialeTekensInNaamException("Geen speciale tekens toegelaten in de naam van de oefening. Deze mogen wel: spatie ._-<>+?!=$%&*()|");
        }
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public LocalDate getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(LocalDate startdatum) {
        this.startdatum = startdatum;
    }

    @Override
    public String toString() {
        return getNaam();
    }
}
