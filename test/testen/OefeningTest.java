/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testen;

import domein.AddGroepsbewerking;
import domein.Doelstelling;
import domein.Groepsbewerking;
import domein.Oefening;
import domein.SubstractGroepsbewerking;
import domein.Vak;
import exceptions.NaamTeKortException;
import exceptions.NaamTeLangException;
import exceptions.SpecialeTekensInNaamException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Daan
 */
@RunWith(value = Parameterized.class)
public class OefeningTest {
    private Oefening oef;
    private String naam;
    private String antwoord;
    private Vak vak;
    private File opgaveFile;
    private File feedbackFile;
    private List<Groepsbewerking> bewerkingList;
    private List<Doelstelling> doelstellingsList;
    boolean resultaat;
   
    

    public OefeningTest(String naam, String antwoord, Vak vak, File opgave, File feedback, List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen, boolean resultaat) {
        oef = new Oefening(naam, antwoord, vak, opgave, feedback, groepsbewerkingen, doelstellingen);
    } 

    
    //public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback,List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {
    @Parameters
    public static Collection getTestParameters() {
        
        return Arrays.asList(new Object[][]{{"Optelsommen", "20", new Vak("Wiskunde"), new File("OpgaveFile"), new File("FeedbackFile"),
            new ArrayList<>(Arrays.asList(
            new AddGroepsbewerking("tel op", 5.0),
            new AddGroepsbewerking("tel op", 10.0),
            new SubstractGroepsbewerking("trek af", 7.00),
            new SubstractGroepsbewerking("trek af", 12.00)
            )), new ArrayList<>(Arrays.asList(
            new Doelstelling("AB", "Doelstelling1"),
            new Doelstelling("CD", "Doelstelling2")
            )), true}});
    }

    @Test(expected = IllegalArgumentException.class)
    public void naamIsNull() {
        oef.setNaam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void naamIsLeeg() {

        oef.setNaam("");
    }

    @Test(expected = SpecialeTekensInNaamException.class)
    public void naamMatchtPatternNiet() {
        oef.setNaam("oefen§ing");
    }

    @Test(expected = NaamTeLangException.class)
    public void naamIsTeLang() {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < 40; i++) {
//            Random r = new Random();
//            char c = (char) (r.nextInt(26) + 'a');
//            sb.append(c);
//        }
//        oef.setNaam(sb.toString());
        oef.setNaam("oefoefoeffoefoefoeffoefoefoeffoefoefoef41");
    }

    @Test(expected = NaamTeKortException.class)
    public void naamIsTeKort() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 1; i++) {
            Random r = new Random();
            char c = (char) (r.nextInt(26) + 'a');
            sb.append(c);
        }
        oef.setNaam(sb.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void antwoordIsNull() {
        oef.setAntwoord(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void antwoordIsLeeg() {
        oef.setAntwoord("");
    }

    @Test(expected = SpecialeTekensInNaamException.class)
    public void antwoordMatchtPatternNiet() {
        oef.setNaam("50\\{");
    }


    @Test(expected = IllegalArgumentException.class)
    public void vakIsNull() {
        oef.setVak(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void opgaveIsNull() {
        oef.setOpgave(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void feedbackIsNull() {
        oef.setFeedback(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groepsbewerkingenIsNull() {
        oef.setGroepsbewerkingen(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doelstellingenIsNull() {
        oef.setDoelstellingen(null);
    }


    


    
    
    
    
   
}
