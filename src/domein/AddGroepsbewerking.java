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
@DiscriminatorValue("AddGroepsbewerking")
public class AddGroepsbewerking extends Groepsbewerking<Double> implements Serializable {

    public AddGroepsbewerking() {
        super();
    }

    public AddGroepsbewerking(String omschrijving, Double teBewerken) {
        super(omschrijving, teBewerken);
    }

    @Override
    public String toString() {

        String[] omschrijvingArray = super.getOmschrijving().split(" ");

        double teBewerken = super.getTeBewerken();
        int getalIfRound = 0;

        if (teBewerken % 1 == 0) {
            return omschrijvingArray[0] + " " + (int) teBewerken + " " + omschrijvingArray[1];
        } else {
            return omschrijvingArray[0] + " " + teBewerken + " " + omschrijvingArray[1];
        }

    }

    @Override
    public Double berekenOplossing(Double teBewerken) {
        return teBewerken + super.getTeBewerken();
    }

}
