/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Daan
 */
@Entity
@Access(AccessType.FIELD)
public class Leerling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String voornaam;
    private String achternaam;

    public Leerling() {
    }

    public Leerling(String voornaam, String achternaam) {
        setVoornaam(voornaam);
        setAchternaam(achternaam);
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        if (voornaam == null || voornaam.trim().isEmpty()) {
            throw new IllegalArgumentException("Je moet een voornaam opgeven");
        }

        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        if (achternaam == null || achternaam.trim().isEmpty()) {
            throw new IllegalArgumentException("Je moet een achternaam opgeven");
        }
        this.achternaam = achternaam;
    }

    @Override
    public String toString() {
        return voornaam + " " + achternaam;
    }


}
