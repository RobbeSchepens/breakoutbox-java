/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Daan
 */
public class BoxController {

    private Box huidigeBox;
    private BoxBeheerder bb = new BoxBeheerder();

    private List<Actie> listActiesVanBoxTemp = new ArrayList<>();
    private List<Oefening> listOefeningenVanBoxTemp = new ArrayList<>();

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


    public void voegBoxToe(String naam, String omschrijving, Vak vak) {
        Box box = new Box(naam, omschrijving, vak, listActiesVanBoxTemp, listOefeningenVanBoxTemp);
        bb.add(box);
    }

    public void setListActiesVanBoxTemp(List<IActie> listActiesVanBoxTemp) {

        List<? extends IActie> lijst = listActiesVanBoxTemp;
        this.listActiesVanBoxTemp = (List<Actie>) lijst;
    }

    public void setListOefeningenVanBoxTemp(List<IOefening> listOefeningenVanBoxTemp) {
        List<? extends IOefening> lijst = listOefeningenVanBoxTemp;
        this.listOefeningenVanBoxTemp = (List<Oefening>) lijst;
    }

    public int CountlistOefeningenVanBoxTemp() {
        return listOefeningenVanBoxTemp.size();
    }

    public int CountlistActiesVanBoxTemp() {
        return listActiesVanBoxTemp.size();
    }


    public void pasBoxAan(String naam, String omschrijving, Vak vak) {

        // De acties zijn onveranderd gebleven
        if (listActiesVanBoxTemp.isEmpty()) {
            listActiesVanBoxTemp = huidigeBox.getActies();
        }
        // De oefn zijn onveranderd gebleven
        if (listOefeningenVanBoxTemp.isEmpty()) {
            listOefeningenVanBoxTemp = huidigeBox.getOefeningen();
        }

        huidigeBox.setNaam(naam);
        huidigeBox.setOmschrijving(omschrijving);
        huidigeBox.setVak(vak);
        huidigeBox.setActies(listActiesVanBoxTemp);
        huidigeBox.setOefeningen(listOefeningenVanBoxTemp);
        
        bb.update(huidigeBox);

    }

}
