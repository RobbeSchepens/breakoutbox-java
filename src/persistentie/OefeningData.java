package persistentie;

import domein.AddGroepsbewerking;
import domein.Doelstelling;
import domein.Groepsbewerking;
import domein.Oefening;
import domein.OefeningBeheerder;
import domein.PDF;
import domein.SubstractGroepsbewerking;
import domein.Vak;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import repository.GenericDaoJpa;

public class OefeningData {

    private final OefeningBeheerder ob;

    public OefeningData(OefeningBeheerder oefeningBeheerder) {
        ob = oefeningBeheerder;
    }

    public void populeerData() {

        List<Groepsbewerking> bewerkingenDatabankLijst = new ArrayList();
        for (double i = 1; i < 15; i++) { // 20 bewerkingen maken
            bewerkingenDatabankLijst.add(new AddGroepsbewerking("Voeg toe", i));
            bewerkingenDatabankLijst.add(new SubstractGroepsbewerking("Trek af", i));
        }

        List<Doelstelling> doelstellingenArray = new ArrayList();
        for (int i = 1; i < 12; i++) { // 12 doelstellingen maken
            doelstellingenArray.add(new Doelstelling("Doelstelling" + i));
        }

        List<Vak> vakken = new ArrayList();
        vakken.add(new Vak("Aardrijkskunde"));
        vakken.add(new Vak("Wiskunde"));
        vakken.add(new Vak("Geschiedenis"));
        vakken.add(new Vak("Natuurkunde"));
        vakken.add(new Vak("Nederlands"));

        vakken.forEach(vak -> ob.addVak(vak));
        bewerkingenDatabankLijst.forEach(bw -> ob.addGroepsbewerking(bw));
        doelstellingenArray.forEach(dls -> ob.addDoelstelling(dls));

        // public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback, List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {
        File opgave1 = new File(System.getProperty("user.dir") +File.separator+ "PDFinit" +File.separator+ "Optelsommen_Opgave.pdf");
        File feedback1 = new File(System.getProperty("user.dir") +File.separator+ "PDFinit" +File.separator+ "Optelsommen_Feedback.pdf");

        ob.addOefening(new Oefening("Optelsommen", "40", vakken.get(2), opgave1, feedback1, bewerkingenDatabankLijst.subList(8, 13), doelstellingenArray.subList(0, 3)));

        File opgave2 = new File(System.getProperty("user.dir") +File.separator+ "PDFinit" +File.separator+ "Vermenigvuldigingen_Opgave.pdf");
        File feedback2 = new File(System.getProperty("user.dir") +File.separator+ "PDFinit" +File.separator+ "Vermenigvuldigingen_Feedback.pdf");
        ob.addOefening(new Oefening("Vermenigvuldigingen", "70", vakken.get(1), opgave2, feedback2, bewerkingenDatabankLijst.subList(0, 7), doelstellingenArray.subList(6, 10)));

        File opgave3 = new File(System.getProperty("user.dir") +File.separator+ "PDFinit" +File.separator+ "Hoofdstad VK_Opgave.pdf");
        File feedback3 = new File(System.getProperty("user.dir") +File.separator+ "PDFinit" +File.separator+ "Hoofdstad VK_Feedback.pdf");
        ob.addOefening(new Oefening("Hoofdstad VK", "Londen", vakken.get(4), opgave3, feedback3, bewerkingenDatabankLijst.subList(3, 10), doelstellingenArray.subList(5, 7)));
        /*GenericDaoJpa bewerkingDoa = new GenericDaoJpa(Groepsbewerking.class);
        GenericDaoJpa doelstellingDoa = new GenericDaoJpa(String.class);
        GenericDaoJpa vakDoa = new GenericDaoJpa(Vak.class);
        bewerkingenDatabankLijst.forEach(bew -> bewerkingDoa.insert(bew));
        vakken.forEach(vak -> {
            vakDoa.insert(vak);
            //System.out.println(vak);
        });
        doelstellingenArray.forEach(doel -> doelstellingDoa.insert(doel));*/
        //public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback, Set<Groepsbewerking> groepsbewerkingen, List<String> doelstellingen) {
        //ob.addOefening(new Oefening("Hoofdstad VK", "London", new Vak("Aardrijkskunde")));
        //ob.addOefening(new Oefening("Optelsommen", "1", new Vak("Wiskunde")));
    }
}
