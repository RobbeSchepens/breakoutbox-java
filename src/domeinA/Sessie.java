/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domeinA;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Daan
 */
@Entity
@Table(name = "sessies")
public class Sessie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessieId;

    private String code;
    private String naam;
    private String omschrijving;

    private Klas klas;
    private Set<Leerling> leerlingen;
    @OneToMany
    private Set<Groep> groepen;

    private Box box;
    private int state;

    protected Sessie() {
    }

    public Sessie(String code, String naam, String omschrijving, Set<Leerling> leerlingen, Box box, int state) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.leerlingen = leerlingen;
        this.box = box;
        this.state = state;
    }

}
