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
@Table(name = "boxen")
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boxId;

    private String naamBox;
    private String omschrijving;
    private Set<Actie> acties;
    private Set<Oefening> Oefeningen;
    private Set<Toegangscode> toegangscodes;

    protected Box() {

    }

    public Box(Set<Actie> acties, Set<Oefening> oefeningen, Set<Toegangscode> toegangscodes, String omschrijving, String naambox) {

        this.naamBox = naambox;
        this.omschrijving = omschrijving;
        this.acties = acties;
        this.Oefeningen = oefeningen;
        this.toegangscodes = toegangscodes;
    }

}
