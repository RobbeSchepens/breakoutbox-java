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
public class Groep {
    private int groepId;
    
    private Collection<Leerling> leerlingen;
    private Pad pad;
    
    protected Groep(){
        
    }
    public Groep(Pad pad, Collection<Leerling> leerlingen){
        this.pad = pad;
        this.leerlingen = leerlingen;
    }
    
    
}
