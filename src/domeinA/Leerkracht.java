/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domeinA;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
@Table(name = "leerkrachten")
public class Leerkracht {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leerkrachtId;

    private String voornaam;
    private String achternaam;
    private String email;
    private Set<Sessie> sessies;
    private Set<Klas> klassen;

    protected Leerkracht() {

    }

    public Leerkracht(String voornaam, String achternaam, String email, Set<Klas> klassen) {
        klassen = klassen;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }

    public void addSessie(Sessie s) {
        sessies.add(s);
    }

}
