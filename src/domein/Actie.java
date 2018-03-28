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
@Table(name = "acties")
public class Actie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ActieId;
    private String omschrijving;

    protected Actie() {

    }

    public Actie(String omschrijving) {
        this.omschrijving = omschrijving;
    }

}
