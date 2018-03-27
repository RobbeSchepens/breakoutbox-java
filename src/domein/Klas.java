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
public class Klas {
    private int klasId;
    private ArrayList<Leerling> leerlingen;
    private Leerkracht leerkracht;
    
    
    protected Klas(){
        
    }
    public Klas(ArrayList<Leerling> leerlingen, Leerkracht leerkracht){
        this.leerkracht = leerkracht;
        this.leerlingen = leerlingen;
    }
    
    
}
