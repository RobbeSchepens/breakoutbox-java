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
public class Box {  
    private int boxId;
    
    private String naamBox;
    private String omschrijving;
    private Collection<Actie> acties;
    private Collection<Oefening> Oefeningen;
    private Collection<Toegangscode> toegangscodes;
      
    protected Box(){
  
    }
    public Box(Collection<Actie> acties, Collection<Oefening> oefeningen, Collection<Toegangscode> toegangscodes, String omschrijving, String naambox)
    {
      
        this.naamBox = naambox;
        this.omschrijving = omschrijving;
        this.acties = acties;
        this.Oefeningen = oefeningen;
        this.toegangscodes = toegangscodes;
    }
    
    
}
