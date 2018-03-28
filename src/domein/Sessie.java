/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Daan
 */
public class Sessie {

    private int sessieId;

    private String code;
    private String naam;
    private String omschrijving;
    private Klas klas;
    private ArrayList<Leerling> leerlingen;
    private ArrayList<Groep> groepen;
    private Box box;
    private int state;

    protected Sessie() {
    }

    public Sessie(String code, String naam, String omschrijving, ArrayList<Leerling> leerlingen, Box box, int state) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.leerlingen = leerlingen;
        this.box = box;
        this.state = state;
    }

    public void MaakRandomGroepenGroepSize(int groepSize) {
        /*Groep groep = new Groep(); // een groep met random ingedeelde personen
        ArrayList<Leerling> leerlingen = this.leerlingen; // een lijst van de huidige leerlingen in een sessie
        ArrayList<Groep> lijstVanGroepen = new ArrayList<>(); // een lijst van de al random ingedeelde groepen
        Random rand = new Random();  
        Collections.shuffle(leerlingen);*/

    }

    public void maakRandomGroepenAantalGroepen(int aantalGroepen) {
        /* Groep groep = new Groep(); 
        ArrayList<Leerling> leerlingen = this.leerlingen; 
        ArrayList<Groep> lijstVanGroepen = new ArrayList<>();
        int groepSize = 0;     
        groepSize = (int)(leerlingen.size() / aantalGroepen);  
        Collections.shuffle(leerlingen);*/

    }

}
