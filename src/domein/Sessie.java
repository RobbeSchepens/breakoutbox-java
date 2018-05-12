package domein;

import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private List<Groep> groepen;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Leerling> leerlingen;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Box box;

    private boolean isActief;
    private boolean isAfstandsonderwijs;
    private String typeGroepen;
    private int aantalGroepen;

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
        if (klas == null)
            throw new IllegalArgumentException("Er werd geen klas opgegeven.");
        this.klas = klas;
    }

    @Override
    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        if (box == null)
            throw new IllegalArgumentException("Er werd geen box opgegeven.");
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
        if (aantalGroepen <= 0)
            throw new IllegalArgumentException("Er werd geen aantal groepen opgegeven.");
        this.aantalGroepen = aantalGroepen;
    }

    @Override
    public LocalDate getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(LocalDate startdatum) {
        if (startdatum.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("De opgegeven datum moet later dan vandaag vallen.");
        this.startdatum = startdatum;
    }

    private void createSessie() {
        switch (typeGroepen) {
            case "auto": ;
                break;
            case "handleerkracht":
                maakGroepenLeeg();
                break;
            case "handleerling":
                maakGroepenLeeg();
                break;
        }

    }

    public void maakGroepenAuto() { // deze methode equalized
        List<Leerling> leerlingenKlas = new ArrayList<>(klas.getLeerlingen());
        List<Groep> groepenInMethode = new ArrayList<>();
        Groep groep;

        Collections.shuffle(leerlingenKlas);
        for (int i = 0; i < aantalGroepen; i++) {
            groep = new Groep(new ArrayList<>());
            groepenInMethode.add(groep);
        }
        boolean test = false;
        while (!test) {
            try {
                groepenInMethode.forEach(item -> {
                    item.addLeerling(leerlingenKlas.get(0));
                    leerlingenKlas.remove(0);
                });
            } catch (Exception e) {
                test = true;
            }
        }
        groepen = groepenInMethode;
    }

    private void maakGroepenLeeg() {
        for (int i = 0; i < aantalGroepen; i++) {
            groepen.add(new Groep(new ArrayList<>()));
        }
    }



    @Override
    public String toString() {
        return getNaam();
    }
}