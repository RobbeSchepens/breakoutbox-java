package domein;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
public class Klas implements IKlas, Serializable {
    
    @Transient
    private long id;
    @Transient
    private SimpleStringProperty naam = new SimpleStringProperty();
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Leerling> leerlingen;

    public Klas() {
    }

    public Klas(String naam, List<Leerling> leerlingen) {
        setNaam(naam);
        setLeerlingen(leerlingen);
    }
    
    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public StringProperty naamProperty() {
        return naam;
    }

    @Override
    @Basic
    @Access(AccessType.PROPERTY)
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Je moet een naam opgeven");
        }
        this.naam.set(naam);
    }

    @Override
    public List<Leerling> getLeerlingen() {
        return leerlingen;
    }

    public void setLeerlingen(List<Leerling> leerlingen) {
        if (leerlingen == null || leerlingen.isEmpty()) {
            throw new IllegalArgumentException("Je moet leerlingen opgeven");
        }
        this.leerlingen = leerlingen;
    }

    @Override
    public String toString() {
        return naam.get();
    }
}