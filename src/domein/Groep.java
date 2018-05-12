/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author Daan
 */
@Entity
@Access(AccessType.FIELD)
public class Groep implements Serializable, IGroep {

    @Transient
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private List<Leerling> leerlingen;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Pad pad;

    public Groep() {
    }

    public Groep(List<Leerling> leerlingen) {
        setLeerlingen(leerlingen);
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

    @Override
    public List<Leerling> getLeerlingen() {
        return leerlingen;
    }


    public void setLeerlingen(List<Leerling> leerligen) {
        /*if (leerligen == null || leerligen.isEmpty()) {
            throw new IllegalArgumentException("Je moet oefeningen opgeven");
        }*/
        this.leerlingen = leerligen;
    }

    public Pad getPad() {

        return pad;
    }

    public void setPad(Pad pad) {
        this.pad = pad;
    }

    public void addLeerling(Leerling leerling) {
        leerlingen.add(leerling);
    }


}
