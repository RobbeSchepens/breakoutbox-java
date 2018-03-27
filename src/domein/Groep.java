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
public class Groep {
    private int groepId;
    
    private ArrayList<Leerling> leerlingen;
    private Pad pad;
    
    protected Groep(){
        
    }
    public Groep(Pad pad, ArrayList<Leerling> leerlingen){
        this.pad = pad;
        this.leerlingen = leerlingen;
    }

    public void setLeerlingen(ArrayList<Leerling> leerlingen) {
        this.leerlingen = leerlingen;
    }

    public void addLeerling(Leerling l){
        leerlingen.add(l);
    }

    public ArrayList<Leerling> getLeerlingen() {
        return leerlingen;
    }
    
    
    
    
    
    
    
}
