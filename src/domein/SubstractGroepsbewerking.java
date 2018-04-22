/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Daan
 */
@Entity
@DiscriminatorValue("SubstractGroepsbewerking")
public class SubstractGroepsbewerking extends Groepsbewerking<Double> {

    public SubstractGroepsbewerking() {
        super();
    }

    public SubstractGroepsbewerking(String omschrijving, Double teBewerken) {
        super(omschrijving, teBewerken);
    }

    @Override
    public String toString() {
        return super.getOmschrijving();
    }

    @Override
    public Double berekenOplossing(Double teBewerken) {
        return teBewerken - super.getTeBewerken();
    }

}
