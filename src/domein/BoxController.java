/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author Daan
 */
public class BoxController {

    private Box huidigeBox;
    private BoxBeheerder bb = new BoxBeheerder();

    public BoxController() {
        this.bb = new BoxBeheerder();
    }

    public ObservableList<IBox> geefBoxen() {
        return (ObservableList<IBox>) bb.getBoxen();
    }

    public ObservableList<Vak> geefVakken() {
        return (ObservableList<Vak>) bb.getVakken();
    }

    public ObservableList<IActie> geefActies() {
        return (ObservableList<IActie>) bb.getActies();
    }

    public ObservableList<IOefening> geefOefeningen() {
        return (ObservableList<IOefening>) bb.getOefeningen();
    }


    public void setHuidigeBox(IBox box) {
        this.huidigeBox = (Box) box;

    }


    public void delete(IBox o) {
        bb.delete((Box) o);
    }

    public void voegBoxToe(String naam, String omschrijving, Vak vak, ObservableList<Actie> acties, ObservableList<Oefening> oefening) {
        // ArrayList<Actie> act = new ArrayList<>(acties);
        //ArrayList<Oefening> oef = new ArrayList<>(oefening);
        Box box = new Box();
        bb.add(box);
    }

    public void pasBoxAan(String naam, String omschrijving, Vak vak, ObservableList<Actie> acties, ObservableList<Oefening> oefening) {
        ArrayList<Actie> act = new ArrayList<>(acties);
        ArrayList<Oefening> oef = new ArrayList<>(oefening);
        huidigeBox.setNaam(naam);
        huidigeBox.setOmschrijving(omschrijving);
        huidigeBox.setVak(vak);
        huidigeBox.setActies(act);
        huidigeBox.setOefeningen(oef);
        
        bb.update(huidigeBox);

    }

}
