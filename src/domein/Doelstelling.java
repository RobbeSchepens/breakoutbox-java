/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Daan
 */
@Entity
public class Doelstelling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String doelstelling;

    public Doelstelling() {

    }

    public Doelstelling(String doelstelling) {
        this.doelstelling = doelstelling;
    }

    public String getDoelstelling() {
        return doelstelling;
    }

    public void setDoelstelling(String doelstelling) {
        this.doelstelling = doelstelling;
    }

    @Override
    public String toString() {
        return doelstelling;
    }

}
