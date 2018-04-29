/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Daan
 */
@Entity
@Access(AccessType.PROPERTY)
public class Klas implements Serializable {

    private long id;
    private SimpleStringProperty naam = new SimpleStringProperty();
    private List<Leerling> leerlingen;

    public Klas() {
    }

    public Klas(String naam, List<Leerling> leerlingen) {
        setNaam(naam);
        setLeerlingen(leerlingen);

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public StringProperty naamProperty() {
        return naam;
    }

    @Column(name = "Naam")
    public String getNaam() {
        return naam.get();
    }

    private void setNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Geen naam voor klas ingegeven");
        }
        this.naam.set(naam);
    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    public List<Leerling> getLeerlingen() {
        return leerlingen;
    }

    public void setLeerlingen(List<Leerling> leerlingen) {
        this.leerlingen = leerlingen;
    }

    @Override
    public String toString() {
        return naam.get();
    }


}
