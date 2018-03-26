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
public class Oefening {
    private int oefeningId;
    
    private String naam;
    private String opgave;
    private String antwoord;
    private GroepsBewerking groepsbewerking;
    private Feedback feedback;
    private Vak vak;
    
    protected Oefening(){
        
    }
    public Oefening(String naam, String opgave, String antwoord, Feedback feedback,  Vak vak){
        this.naam = naam;
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.feedback = feedback;
        this.vak = vak;      
    }

    public GroepsBewerking getGroepsbewerking() {
        return groepsbewerking;
    }

    public void setGroepsbewerking(GroepsBewerking groepsbewerking) {
        this.groepsbewerking = groepsbewerking;
    }
    
    
    
    
    
    
    
}
