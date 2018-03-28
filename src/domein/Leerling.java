/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * @author Daan
 */
@Entity
@Table(name = "leerlingen")
public class Leerling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leerlingId;
    private String voornaam;
    private String achternaam;

    protected Leerling() {

    }

    public Leerling(String voornaam, String achternaam) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }

}
