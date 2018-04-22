package persistentie;

import domein.AddGroepsbewerking;
import domein.Doelstelling;
import domein.Groepsbewerking;
import domein.Oefening;
import domein.OefeningBeheerder;
import domein.SubstractGroepsbewerking;
import domein.Vak;
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
            bewerkingenDatabankLijst.add(new AddGroepsbewerking("Voeg 1 toe", i));
            bewerkingenDatabankLijst.add(new SubstractGroepsbewerking("Trek 1 af", i));
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
        vakken.add(new Vak("Nederalnds"));

        GenericDaoJpa bewerkingDoa = new GenericDaoJpa(Groepsbewerking.class);
        GenericDaoJpa doelstellingDoa = new GenericDaoJpa(String.class);
        GenericDaoJpa vakDoa = new GenericDaoJpa(Vak.class);

        bewerkingenDatabankLijst.forEach(bew -> bewerkingDoa.insert(bew));
        vakken.forEach(vak -> vakDoa.insert(vak));
        doelstellingenArray.forEach(doel -> doelstellingDoa.insert(doel));

        //public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback, Set<Groepsbewerking> groepsbewerkingen, List<String> doelstellingen) {
        //ob.addOefening(new Oefening("Hoofdstad VK", "London", new Vak("Aardrijkskunde")));
        //ob.addOefening(new Oefening("Optelsommen", "1", new Vak("Wiskunde")));
    }
}
