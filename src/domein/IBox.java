/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Daan
 */
public interface IBox {

    String getNaam();

    String getOmschrijving();

    StringProperty naamProperty();

    List<Actie> getActies();

    int getActiesCount();
    
    Vak getVak();

    List<Oefening> getOefeningen();


}
