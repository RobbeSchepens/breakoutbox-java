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
public class Klas {
    private int klasId;
    private Collection<Leerling> leerlingen;
    private Leerkracht leerkracht;
    
    
    protected Klas(){
        
    }
    public Klas(Collection<Leerling> leerlingen, Leerkracht leerkracht){
        this.leerkracht = leerkracht;
        this.leerlingen = leerlingen;
    }
    
    
}
