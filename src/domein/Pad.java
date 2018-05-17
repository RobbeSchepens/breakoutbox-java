package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity(name = "Padd") // Pad is an sql keyword, therefor we us Padd
@Access(AccessType.FIELD)
public class Pad implements Serializable {

    @Transient
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Toegangscode> toegangsCodes = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Oefening> oefeningen = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Groepsbewerking> groepsBewerkingen = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Actie> acties = new ArrayList<>();

    public Pad() {
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

    public List<Toegangscode> getToegangsCodes() {
        return toegangsCodes;
    }

    public void setToegangsCodes(List<Toegangscode> toegangsCodes) {
        this.toegangsCodes = toegangsCodes;
    }

    public List<Oefening> getOefeningen() {
        return oefeningen;
    }

    public void setOefeningen(List<Oefening> oefeningen) {
        this.oefeningen = oefeningen;
    }

    public List<Groepsbewerking> getGroepsBewerkingen() {
        return groepsBewerkingen;
    }

    public void setGroepsBewerkingen(List<Groepsbewerking> GroepsBewerkingen) {
        this.groepsBewerkingen = GroepsBewerkingen;
    }

    public void addActie(Actie actie) {
        acties.add(actie);
    }

    public void addOefening(Oefening oefening) {
        oefeningen.add(oefening);
    }

    public void addGroepsbewerking(Groepsbewerking groepsbewerking) {
        groepsBewerkingen.add(groepsbewerking);
    }

    public void addToegangscode(Toegangscode toegangscode) {

    }

}
