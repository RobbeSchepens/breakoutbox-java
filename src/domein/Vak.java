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
@Table(name = "vakken")
public class Vak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int vakId;

    public String naam;
    public String omschrijving;

    protected Vak() {

    }

    public Vak(String naam, String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

}
