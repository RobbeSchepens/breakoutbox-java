/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
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
import javax.persistence.Transient;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Daan
 */
@Entity
@Access(AccessType.FIELD)
public class Box implements IBox, Serializable {
    @Transient
    private long id;
    @Transient
    private SimpleStringProperty naam = new SimpleStringProperty();

    private String omschrijving;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Vak vak;
    //private List<Toegangscode> toegangscodes;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Actie> acties;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Oefening> oefeningen;

    public Box() {


    }

    public Box(String naam, String omschrijving, Vak vak, List<Actie> acties, List<Oefening> oefeningen) {

        setNaam(naam);
        setOmschrijving(omschrijving);
        setVak(vak);

        setActies(acties);

        setOefeningen(oefeningen);
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

    @Override
    public StringProperty naamProperty() {
        return naam;
    }

    @Override
    @Basic
    @Access(AccessType.PROPERTY)
    public String getNaam() {
    return naam.get();
    }

    public void setNaam(String naam) {
        if (naam == null || naam.trim().isEmpty()) {
            throw new IllegalArgumentException("Je moet een naam opgeven");
        }
        this.naam.set(naam);
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        if (omschrijving == null || omschrijving.trim().isEmpty()) {
            throw new IllegalArgumentException("Je moet een omschrijving opgeven");
        }
        this.omschrijving = omschrijving;
    }

    @Override
    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        if (vak == null) {
            throw new IllegalArgumentException("Je moet een vak opgeven");
        }
        this.vak = vak;
    }


    @Override
    public List<Actie> getActies() {
        return acties;
    }
    public void setActies(List<Actie> acties) {
        if (acties == null) {
            throw new IllegalArgumentException("Je moet acties opgeven");
        }
        this.acties = acties;
    }

    public int getActiesCount() {
        return getActies().size();
    }

    @Override
    public List<Oefening> getOefeningen() {
        return oefeningen;
    }
    public void setOefeningen(List<Oefening> oefeningen) {
        if (oefeningen == null || oefeningen.isEmpty()) {
            throw new IllegalArgumentException("Je moet oefeningen opgeven");
        }
        this.oefeningen = oefeningen;
    }


    @Override
    public String toString() {
        return naam.get();
    }

    void createPdf(File selectedDirectory) { // naam van box, naam van oefeningen met zijn doelstellingen

        if (selectedDirectory != null) {
            try (PDDocument pdf = new PDDocument()) {

                PDPage blankPage = new PDPage(PDRectangle.A4);
                pdf.addPage(blankPage);
                PDFont font = PDType1Font.HELVETICA;
                try (PDPageContentStream contentStream = new PDPageContentStream(pdf, blankPage)) {
                    contentStream.beginText();
                    contentStream.setFont(font, 12);
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(70, 750);

                    StringBuilder sb = new StringBuilder(String.format("BoxNaam: %s", getNaam()));
                    sb.append("\n");
                    sb.append("\n");

                    for (Oefening oef : getOefeningen()) {
                        sb.append("Oefening: " + oef.getNaam() + "\n----------------------------------------\n");
                        for (Doelstelling doels : oef.getDoelstellingen()) {
                            sb.append(String.format("%s\n", doels));
                        }
                        sb.append("\n");
                    }

                    String tekst = sb.toString();
                    String[] lijnen = tekst.split("\\r?\\n");
                    Arrays.stream(lijnen).forEach(lijn -> {
                        try {
                            contentStream.showText(lijn);
                            contentStream.newLine();
                        } catch (IOException ex) {
                            throw new RuntimeException("error");
                        }
                    });

                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.endText();
                }

                pdf.save(String.format("%s\\%s.pdf", selectedDirectory.getAbsolutePath(), getNaam()));
            } catch (IOException | RuntimeException ex) {
                throw new RuntimeException("Fout tijdens pdf creatie");
            }
        }
    }
}
