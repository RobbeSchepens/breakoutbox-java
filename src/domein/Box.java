/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;

/**
 *
 * @author Daan
 */
public class Box {  
    private int boxId;
    
    private String naamBox;
    private String omschrijving;
    private ArrayList<Actie> acties;
    private ArrayList<Oefening> Oefeningen;
    private ArrayList<Toegangscode> toegangscodes;
      
    protected Box(){
  
    }
    public Box(ArrayList<Actie> acties, ArrayList<Oefening> oefeningen, ArrayList<Toegangscode> toegangscodes, String omschrijving, String naambox)
    {
      
        this.naamBox = naambox;
        this.omschrijving = omschrijving;
        this.acties = acties;
        this.Oefeningen = oefeningen;
        this.toegangscodes = toegangscodes;
    }
    
    
    
    
    
}
