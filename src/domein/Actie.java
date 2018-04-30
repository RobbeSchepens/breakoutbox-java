/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Daan
 */
@Entity
@Access(AccessType.PROPERTY)
public class Actie implements IActie, Serializable {

    private long id;
    private SimpleStringProperty naam = new SimpleStringProperty();

    public Actie() {
    }
    
    public Actie(String naam) {
        setNaam(naam);
    }

    @Id
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

    @Column(name = "Naam")
    @Override
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Geen naam voor actie ingegeven");
        }
        this.naam.set(naam);
    }

    @Override
    public String toString() {
        return naam.get(); //To change body of generated methods, choose Tools | Templates.
    }


}
