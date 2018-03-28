/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Daan
 */
public class Actie {

    private int ActieId;

    private String omschrijving;

    protected Actie() {

    }

    public Actie(String omschrijving) {
        this.omschrijving = omschrijving;
    }

}
