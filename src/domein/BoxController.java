package domein;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoxController {

    private Box huidigeBox;
    private BoxBeheerder bb;
    private List<Actie> listActiesVanBoxTemp = new ArrayList<>();
    private List<Oefening> listOefeningenVanBoxTemp = new ArrayList<>();

    public BoxController() {
        this.bb = new BoxBeheerder();
    }

    public BoxBeheerder getBb() {
        return bb;
    }

    public ObservableList<IBox> geefBoxen() {
        return bb.getBoxenFiltered();
    }

    public int geefAantalBoxen() {
        return bb.getBoxen().size();
    }

    public void setHuidigeBox(IBox box) {
        this.huidigeBox = (Box) box;
    }
    
    public IBox getHuidigeBox() {
        return huidigeBox;
    }

    public void voegBoxToe(String naam, String omschrijving, Vak vak) {
        Box box = new Box(naam, omschrijving, vak, listActiesVanBoxTemp, listOefeningenVanBoxTemp);
        bb.add(box);
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

    public void delete(IBox o) {
        bb.delete((Box) o);
    }
    
    public void veranderFilter(String filterValue) {
        bb.veranderFilter(filterValue);
    }
    
    public ObservableList<Vak> geefVakken() {
        return bb.getVakken();
    }

    public ObservableList<IActie> geefActies() {
        return (ObservableList<IActie>) bb.getActies();
    }

    public ObservableList<IOefening> geefOefeningen() {
        return (ObservableList<IOefening>) bb.getOefeningen();
    }
    
    public void setListActiesVanBox(List<? extends IActie> list) {
        this.listActiesVanBoxTemp = (List<Actie>)list;
    }

    public void setListOefeningenVanBox(List<? extends IOefening> list) {
        this.listOefeningenVanBoxTemp = (List<Oefening>)list;
    }
    
    public int getAantalTempActies() {
        return this.listActiesVanBoxTemp.size();
    }
    
    public int getAantalTempOefeningen() {
        return this.listOefeningenVanBoxTemp.size();
    }
    
    public List<Oefening> getOefeningenListTemp() {
        return this.listOefeningenVanBoxTemp;
    }
    
    public ObservableList<IActie> geefActiesHuidigeBox() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(huidigeBox.getActies()));
    }
    
    public ObservableList<IOefening> geefOefeningenHuidigeBox() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(huidigeBox.getOefeningen()));
    }

    public void createPdf(File selectedDirectory) {
        huidigeBox.createPdf(selectedDirectory);
    }
}