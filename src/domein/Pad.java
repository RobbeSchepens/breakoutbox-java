/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.ArrayList;

/**
 *
 * @author Daan
 */
public class Pad {
    private int padId;
    private ArrayList<Opdracht> opdrachten = new ArrayList<>();  
    protected Pad(){
        
    }
    
    public void addOpdracht(Opdracht o){
        opdrachten.add(o);
    }
    
}
