/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domeinA;

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
@Table(name = "groepen")
public class Groep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groepId;

    private Set<Leerling> leerlingen;
    private Pad pad;

    protected Groep() {

    }

    public Groep(Pad pad, Set<Leerling> leerlingen) {
        this.pad = pad;
        this.leerlingen = leerlingen;
    }

    public void setLeerlingen(Set<Leerling> leerlingen) {
        this.leerlingen = leerlingen;
    }

    public void addLeerling(Leerling l) {
        leerlingen.add(l);
    }

    public Set<Leerling> getLeerlingen() {
        return leerlingen;
    }

}
