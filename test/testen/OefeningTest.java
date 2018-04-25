/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testen;

import domein.Doelstelling;
import domein.Groepsbewerking;
import domein.Oefening;
import domein.Vak;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Daan
 */
@RunWith(value = Parameterized.class)
public class OefeningTest {
    
    private String naam;
    private Oefening oef;
    private List<Groepsbewerking> bewerkingList;
    private List<Doelstelling> doelstellingsLijst;
    private File opgaveFile;
    private File feedbackFile;
    private Vak vak;

    public OefeningTest() {

        vak = new Vak("Wiskunde");
    }
    
    
    
    
    
    
   
}
