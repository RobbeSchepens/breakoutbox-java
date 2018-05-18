package domein;

import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Sessie.findByName", query = "select e from Sessie e where e.naam = :sessienaam")
})
public class Sessie implements ISessie, Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private Long id;

    @Transient
    private final StringProperty naam = new SimpleStringProperty();
    private String code;
    private String omschrijving;
    private LocalDate startdatum;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Klas klas;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Groep> groepen = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Leerling> leerlingen = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Box box;

    private boolean isActief;
    private boolean isAfstandsonderwijs;
    private String typeGroepen;
    private int aantalGroepen;
    Random randomGenerator = new Random();

    public Sessie() {
    }

    public Sessie(String naam, String omschrijving, Klas klas, Box box, boolean afstand,
            String typeGroepen, int aantalGroepen, LocalDate startdatum) {
        setNaam(naam);
        setCode("XYZ");
        setOmschrijving(omschrijving);
        setKlas(klas);
        setBox(box);
        setIsAfstandsonderwijs(isAfstandsonderwijs);
        setTypeGroepen(typeGroepen);
        setAantalGroepen(aantalGroepen);
        setStartdatum(startdatum);

        generateEverything();
        // generateEverything();
    }

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    @Basic
    @Access(AccessType.PROPERTY)
    public String getNaam() {
        return naam.get();
    }

    public void setNaam(String value) {
        controleerNaam(value);
        naam.set(value);
    }

    public List<Groep> getGroepen() {
        return groepen;
    }

    public void setGroepen(List<Groep> groepen) {
        this.groepen = groepen;
    }

    @Override
    public StringProperty naamProperty() {
        return naam;
    }

    private void controleerNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Er werd geen naam opgegeven.");
        }
        if (naam.length() < 3) {
            throw new NaamTeKortException("Naam moet minstens 3 tekens lang zijn!");
        }
        if (naam.length() > 40) {
            throw new NaamTeLangException("Naam mag maximum 40 karakters bevatten!");
        }

        // Deze karakters mogen, alle andere niet. 
        Pattern p = Pattern.compile("[^A-Za-z0-9._\\-<>+?!=$%&*()| ]");
        Matcher m = p.matcher(naam);
        if (m.find()) {
            throw new SpecialeTekensInNaamException("Geen speciale tekens toegelaten in de naam van de oefening. Deze mogen wel: spatie ._-<>+?!=$%&*()|");
        }
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public Klas getKlas() {
        return klas;
    }

    public void setKlas(Klas klas) {
        if (klas == null) {
            throw new IllegalArgumentException("Er werd geen klas opgegeven.");
        }
        this.klas = klas;
    }

    @Override
    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        if (box == null) {
            throw new IllegalArgumentException("Er werd geen box opgegeven.");
        }
        this.box = box;
    }

    @Override
    public boolean isAfstandsonderwijs() {
        return isAfstandsonderwijs;
    }

    public void setIsAfstandsonderwijs(boolean isAfstandsonderwijs) {
        this.isAfstandsonderwijs = isAfstandsonderwijs;
    }

    @Override
    public String getTypeGroepen() {
        return typeGroepen;
    }

    public void setTypeGroepen(String typeGroepen) {
        if (typeGroepen == null || typeGroepen.trim().isEmpty()) {
            throw new IllegalArgumentException("Het type groepen werd niet opgegeven.");
        }
        this.typeGroepen = typeGroepen;
    }

    @Override
    public int getAantalGroepen() {
        return aantalGroepen;
    }

    public void setAantalGroepen(int aantalGroepen) {
        if (aantalGroepen <= 0) {
            throw new IllegalArgumentException("Er werd geen aantal groepen opgegeven.");
        }
        this.aantalGroepen = aantalGroepen;
    }

    @Override
    public LocalDate getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(LocalDate startdatum) {
        if (startdatum.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("De opgegeven datum moet later dan vandaag vallen.");
        }
        this.startdatum = startdatum;
    }

    private void maakGroepenLeeg() {
        for (int i = 0; i < aantalGroepen; i++) {
            groepen.add(new Groep(new ArrayList<>()));
        }
    }

    public void maakGroepenAuto() { // deze methode equalized
        List<Leerling> leerlingenKlas = new ArrayList<>(klas.getLeerlingen());
        Collections.shuffle(leerlingenKlas);
        maakGroepenLeeg();
        for (int i = 0; i < leerlingenKlas.size(); i = i + aantalGroepen) {
            for (int j = 0; j < aantalGroepen; j++) {
                try {
                    groepen.get(j).addLeerling(leerlingenKlas.get(i + j));
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

    private void geefToegangscodesPerGroep() { // elke groep heeft per oefeninng, zelf random genereren ofzo
        StringBuffer sb = new StringBuffer();

        Random r = new Random();
        char c;

        for (Groep groep : groepen) { // elke groep doorgaan
            for (int z = 0; z < box.getOefeningen().size(); z++) { // toegangscode aan elk  oefening in een pad geven
                sb = new StringBuffer();
                for (int i = 0; i < 4; i++) { // random toegangscode van 4 letters
                    r = new Random();
                    c = (char) (r.nextInt(26) + 'a');
                    sb.append(c);
                }
                groep.getPad().addToegangscode(new Toegangscode(sb.toString()));
            }
        }
    }

    private void geefGroepsbewerkingenPerGroep() { // elke oefening in een groep heeft een groepsbewerking

        List<Groepsbewerking> gbwVanOefInPad = null;

        for (Groep groep : groepen) {
            //groepsbewerkingenVanPad = null;
            for (int i = 0; i < box.getOefeningen().size(); i++) {
                gbwVanOefInPad = groep.getPad().getOefeningen().get(i).getGroepsBewerkingen();
                randomGenerator = new Random();
                int indexRandom = randomGenerator.nextInt(gbwVanOefInPad.size());
                groep.getPad().addGroepsbewerking(gbwVanOefInPad.get(indexRandom));
            }
        }
    }

    private void setOefeningenActiesPerGroep() { // een random volgorde van oefenineingen per groep

        List<Actie> actieLijst = new ArrayList<>(box.getActies());
        Collections.shuffle(actieLijst);
        List<Oefening> oefeningenLijst = new ArrayList<>(box.getOefeningen());
        List<List<Object>> listOefActie = new ArrayList<List<Object>>();

        for (int i = 0; i < box.getOefeningen().size(); i++) {
            listOefActie.add(new ArrayList<>(Arrays.asList(oefeningenLijst.get(i), actieLijst.get(i))));
        }
        for (Groep groep : groepen) {
            Collections.shuffle(listOefActie);

            for (int i = 0; i < listOefActie.size(); i++) {
                groep.getPad().addOefening((Oefening) listOefActie.get(i).get(0));

                if (i < listOefActie.size() - 1) {
                    groep.getPad().addActie((Actie) listOefActie.get(i).get(1));
                } else {
                    System.out.println("toevoeging laatste actie (schatkist zoeken)");
                    Actie at = (Actie) listOefActie.get(i).get(1);
                    at.setNaam("Schatkist zoeken");
                    groep.getPad().addActie(at);
                }
            }
        }
    }
    private void geefActiesPerGroep() {
        List<Actie> acties = box.getActies();

    }
    @Override
    public String toString() {
        return getNaam();
    }

    //setOefeningenActiesPerGroep
    //geefGroepsbewerkingenPerGroep
    //geefToegangscodesPerGroep
    //maakGroepenAuto
    //maakGroepenLeeg
    private void generateEverything() {
        switch (typeGroepen) {
            case "auto":
                maakGroepenAuto();
                geefToegangscodesPerGroep();
                System.out.println("in auto");
                break;
            case "handleerkracht":
                maakGroepenLeeg();
                break;
            case "handleerling":
                maakGroepenLeeg();
                break;
        }

    }
}
