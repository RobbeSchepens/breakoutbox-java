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
import javax.persistence.ManyToOne;

/**
 *
 * @author Daan
 */
@Entity
@Access(AccessType.PROPERTY)
public class Box implements IBox, Serializable {

    private long id;
    private SimpleStringProperty naam = new SimpleStringProperty();
    private String omschrijving;
    private Vak vak;
    //private List<Toegangscode> toegangscodes;
    private List<Actie> acties;
    private List<Oefening> oefeningen;

    public Box() {


    }

    public Box(String naam, String omschrijving, Vak vak, List<Actie> acties, List<Oefening> oefeningen) {

        setNaam(naam);
        setOmschrijving(omschrijving);
        setVak(vak);
        setActies(acties);
        setOefeningen(oefeningen);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
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

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }


    @Override
    @ManyToMany(cascade = CascadeType.PERSIST)
    public List<Actie> getActies() {
        return acties;
    }

    @Override
    @ManyToMany(cascade = CascadeType.PERSIST)
    public List<Oefening> getOefeningen() {
        return oefeningen;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNaam(String naam) {
        /*if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Je moet een naam opgeven");
        }*/
        this.naam.set(naam);
    }

    public void setOmschrijving(String omschrijving) {
        /*if (omschrijving == null || omschrijving.trim().isEmpty()) {
            throw new IllegalArgumentException("Je moet een omschrijving opgeven");
        }*/
        this.omschrijving = omschrijving;
    }

    public void setVak(Vak vak) {
        /*if (vak == null) {
            throw new IllegalArgumentException("Je moet een vak opgeven");
        }*/
        this.vak = vak;
    }

    public void setActies(List<Actie> acties) {
        /* if (acties == null || acties.isEmpty()) {
            throw new IllegalArgumentException("Je moet acties opgeven");
        }*/
        this.acties = acties;
    }

    public void setOefeningen(List<Oefening> oefeningen) {
        /* if (oefeningen == null || oefeningen.isEmpty()) {
            throw new IllegalArgumentException("Je moet oefeningen opgeven");
        }*/

        this.oefeningen = oefeningen;
    }

    @Override
    @ManyToOne(cascade = CascadeType.PERSIST)
    public Vak getVak() {
        return vak;
    }

    @Override
    public String toString() {
        return naam.get();
    }


}
