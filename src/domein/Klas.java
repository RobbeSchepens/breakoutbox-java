/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
@Access(AccessType.FIELD)
public class Klas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Transient
    private SimpleStringProperty naam = new SimpleStringProperty();
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Leerling> leerlingen;

    public Klas(String naam, List<Leerling> leerlingen) {
        setNaam(naam);
        setLeerlingen(leerlingen);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public StringProperty naamProperty() {
        return naam;
    }

    @Access(AccessType.PROPERTY)
    public String getNaam() {
        return naam.get();
    }


    private void setNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Geen naam voor klas ingegeven");
        }
        this.naam.set(naam);
    }

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
