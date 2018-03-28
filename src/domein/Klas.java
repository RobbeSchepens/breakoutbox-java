/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.Set;
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
@Table(name = "klassen")
public class Klas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int klasId;
    private Set<Leerling> leerlingen;
    private Leerkracht leerkracht;

    protected Klas() {

    }

    public Klas(Set<Leerling> leerlingen, Leerkracht leerkracht) {
        this.leerkracht = leerkracht;
        this.leerlingen = leerlingen;
    }

}
