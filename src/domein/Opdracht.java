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
@Table(name = "opdrachten")
public class Opdracht {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OpdrachtId;

    private int volgNr;
    private Actie actie;
    private Oefening oefening;
    private Toegangscode toegangscode;

    protected Opdracht() {

    }

    public Opdracht(int volgNr, Actie actie, Oefening oefening, Toegangscode toegangscode) {
        this.volgNr = volgNr;
        this.actie = actie;
        this.oefening = oefening;
        this.toegangscode = toegangscode;
    }

}
