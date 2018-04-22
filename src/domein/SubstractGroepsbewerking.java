/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 *
 * @author Daan
 */
@Entity
@DiscriminatorValue("SubstractGroepsbewerking")
public class SubstractGroepsbewerking extends Groepsbewerking<Double> implements Serializable {

    public SubstractGroepsbewerking() {
        super();
    }

    public SubstractGroepsbewerking(String omschrijving, Double teBewerken) {
        super(omschrijving, teBewerken);
    }

    @Override
    public String toString() {

        String[] omschrijvingArray = super.getOmschrijving().split(" ");

        double teBewerken = super.getTeBewerken();
        if (teBewerken % 1 == 0) {
            teBewerken = (int) teBewerken;
        }

        return omschrijvingArray[0] + " " + (int) teBewerken + " " + omschrijvingArray[1];

    }

    @Override
    public Double berekenOplossing(Double teBewerken) {
        return teBewerken - super.getTeBewerken();
    }

}
