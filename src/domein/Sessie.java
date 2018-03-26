/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.Collection;

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
    private Collection<Groep> groepen;
    private Box box;
    private int state;
    
    protected Sessie(){
        
    }
    public Sessie(String code, String naam, String omschrijving, Collection<Groep> groepen, Box box, int state){
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.groepen = groepen;
        this.box = box;
        this.state = state;
    }
    
    
    
}
