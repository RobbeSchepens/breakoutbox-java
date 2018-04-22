/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Daan
 */
@Entity
public class Doelstelling implements Serializable {

    @Id
    private String doelstelling;

    public Doelstelling() {

    }

    public Doelstelling(String doelstelling) {
        this.doelstelling = doelstelling;
    }

    @Override
    public String toString() {
        return doelstelling;
    }

}
