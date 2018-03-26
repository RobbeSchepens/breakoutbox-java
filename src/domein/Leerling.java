/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Daan
 */
public class Leerling {
    private int leerlingId;
    private String voornaam;
    private String achternaam;  
    protected Leerling(){
        
    }
    public Leerling(String voornaam, String achternaam){
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }
    
    
}
