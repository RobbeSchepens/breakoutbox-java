/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author Daan
 */
@Entity
@Access(AccessType.FIELD)
public class Toegangscode implements Serializable {
    @Transient
    private long id;
    
    private String toegangscode;

    public Toegangscode() {
    }
    
    public Toegangscode(String toegangscode) {
        setToegangscode(toegangscode);
    }

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getToegangscode() {
        return toegangscode;
    }
    
    public void setToegangscode(String toegangscode) {
        this.toegangscode = toegangscode;
    }
    

}
