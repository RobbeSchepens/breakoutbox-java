package domein;

import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.File;
import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Oefening.findByName", query = "select e from Oefening e where e.naam = :oefeningnaam")
})
public class Oefening implements IOefening, Serializable {

    private static final long serialVersionUID = 1L;

    // De ID is gewijzigd naar een getter getId nadat naam een SimpleProperty moest worden en 
    // @Basic moet zijn om aanspreekbaar te zijn in de named query.
    @Transient
    private Long id;

    @Transient
    private final StringProperty naam = new SimpleStringProperty();
    private String antwoord;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Vak vak;

    @OneToOne(cascade = CascadeType.PERSIST)
    private PDF opgave;
    @OneToOne(cascade = CascadeType.PERSIST)
    private PDF feedback;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Doelstelling> doelstellingen;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Groepsbewerking> groepsbewerkingen;

    public Oefening() {
    }

    public Oefening(String naam, String antwoord, Vak vak,
            File opgave, String opgaveNaam,
            File feedback, String feedbackNaam,
            List<Groepsbewerking> groepsbewerkingen,
            List<Doelstelling> doelstellingen) {

        roepSettersAan(naam, antwoord, vak, opgave, opgaveNaam,
                feedback, feedbackNaam, groepsbewerkingen, doelstellingen);
    }

    public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback,
            List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {
        setGroepsbewerkingen(groepsbewerkingen);
        setVak(vak);

        setOpgave(opgave);

        setFeedback(feedback);

        setAntwoord(antwoord);
        setNaam(naam);
        setDoelstellingen(doelstellingen);
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
    public String getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(String antwoord) {
        controleerAntwoord(antwoord);
        this.antwoord = antwoord;
    }

    private void controleerAntwoord(String antwoord) {
        if (antwoord == null || antwoord.equals("")) {
            throw new IllegalArgumentException("Er werd geen antwoord opgegeven.");
        }
        if (antwoord.length() < 1) {
            throw new NaamTeKortException("Antwoord moet minstens 3 tekens lang zijn!");
        }
        if (antwoord.length() > 40) {
            throw new NaamTeLangException("Antwoord mag maximum 40 karakters bevatten!");
        }

        // Deze karakters mogen, alle andere niet. 
        Pattern p = Pattern.compile("[^A-Za-z0-9/=+\\-$%&*()| €£]");
        Matcher m = p.matcher(antwoord);
        if (m.find()) {
            throw new SpecialeTekensInNaamException("Geen speciale tekens toegelaten in het antwoord van de oefening. Deze mogen wel: spatie /=+\\-$%&*()|€£");
        }
    }

    @Override
    public PDF getOpgave() {
        return opgave;
    }

    public void setOpgave(File opgave) {
        if (opgave == null) {
            throw new IllegalArgumentException("Selecteer een PDF om toe te voegen als opgave.");
        }
        this.opgave = new PDF(opgave, String.format("%s", opgave.getName()));
    }

    @Override
    public PDF getFeedback() {
        return feedback;
    }

    public void setFeedback(File feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Selecteer een PDF om toe te voegen als feedback.");
        }
        this.feedback = new PDF(feedback, String.format("%s", feedback.getName()));
    }

    @Override
    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        if (vak == null) {
            throw new IllegalArgumentException("Selecteer een vak voor de oefening.");
        }
        this.vak = vak;
    }

    @Override
    public List<Groepsbewerking> getGroepsBewerkingen() {
        return this.groepsbewerkingen;
    }

    public void setGroepsbewerkingen(List<Groepsbewerking> groepsbewerkingen) {
        System.out.println(groepsbewerkingen + " in de setter van oefening");
        if (groepsbewerkingen == null || groepsbewerkingen.isEmpty()) {
            throw new IllegalArgumentException("Er werden geen groepsbewerkingen geselecteerd.");
        }
        this.groepsbewerkingen = groepsbewerkingen;
        System.out.println(this.groepsbewerkingen + " is attribuut, heeft de juiste objecten"); // Heeft de bewerkingen
        System.out.println(getGroepsBewerkingen() + " is getter, heeft null"); // Heeft null 
    }

    @Override
    public List<Doelstelling> getDoelstellingen() {
        return doelstellingen;
    }

    public void setDoelstellingen(List<Doelstelling> doelstellingen) {
        if (doelstellingen == null || doelstellingen.isEmpty()) {
            throw new IllegalArgumentException("Er werden geen doelstellingen geselecteerd.");
        }
        this.doelstellingen = doelstellingen;
    }

    @Override
    public String toString() {
        return getNaam();
    }

    void roepSettersAan(String naam, String antwoord, Vak vak, File opgave, File feedback, List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {
        setNaam(naam);
        setAntwoord(antwoord);
        setVak(vak);
        setOpgave(opgave);
        setFeedback(feedback);
        setGroepsbewerkingen(groepsbewerkingen);
        setDoelstellingen(doelstellingen);
    }

    void roepSettersAan(String naam, String antwoord, Vak vak,
            File opgave, String opgaveNaam,
            File feedback, String feedbackNaam,
            List<Groepsbewerking> groepsbewerkingen,
            List<Doelstelling> doelstellingen) {
        setNaam(naam);
        setAntwoord(antwoord);
        setVak(vak);
        setOpgave(opgave);
        setFeedback(feedback);
        setGroepsbewerkingen(groepsbewerkingen);
        setDoelstellingen(doelstellingen);
    }

}
