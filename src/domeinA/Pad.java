/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domeinA;

import java.util.ArrayList;
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
@Table(name = "paden")
public class Pad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int padId;
    private Set<Opdracht> opdrachten;

    protected Pad() {

    }

    public void addOpdracht(Opdracht o) {
        opdrachten.add(o);
    }

}
