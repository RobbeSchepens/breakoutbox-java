/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Daan
 */
public class Leerkracht {
    private int leerkrachtId;
    
    private String voornaam;
    private String achternaam;
    private String email;
    private ArrayList<Sessie> sessies;
    private ArrayList<Klas> klassen;
    
    protected Leerkracht(){
        
    }
    public Leerkracht(String voornaam, String achternaam, String email, ArrayList<Klas> klassen){   
        sessies = new ArrayList<>();
        klassen = klassen; 
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email; 
    }
    public void addSessie(Sessie s){
        sessies.add(s);
    }
 
    
}
