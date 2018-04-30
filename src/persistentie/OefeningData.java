package persistentie;

import domein.Actie;
import domein.AddGroepsbewerking;
import domein.Box;
import domein.BoxBeheerder;
import domein.Doelstelling;
import domein.Groepsbewerking;
import domein.Klas;
import domein.Leerling;
import domein.Oefening;
import domein.OefeningBeheerder;
import domein.SubstractGroepsbewerking;
import domein.Vak;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import repository.GenericDaoJpa;
import repository.KlasDaoJpa;

public class OefeningData {

    private final OefeningBeheerder ob;
    private final BoxBeheerder bb;

    public OefeningData(OefeningBeheerder oefeningBeheerder, BoxBeheerder boxbeheerder) {
        ob = oefeningBeheerder;
        bb = boxbeheerder;
        populeerData();
    }

    private void populeerData() {
        List<Groepsbewerking> bewerkingenDatabankLijst = new ArrayList();
        for (double i = 1; i < 7; i++) { // 20 bewerkingen maken
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

        List<Leerling> leerlingen = new ArrayList<>(Arrays.asList(
                new Leerling("Andrea", "Van Dijk"),
                new Leerling("Henk", "Bakker"),
                new Leerling("Stephanie", "Mulder"),
                new Leerling("Tom", "De Groot"),
                new Leerling("Lily", "Bos"),
                new Leerling("Jayden", "Hendriks"),
                new Leerling("Pamela", "Dekker"),
                new Leerling("Luc", "Dijkstra"),
                new Leerling("Eva", "Jacobs"),
                new Leerling("Harry", "Vermeulen"),
                new Leerling("Katy", "Schouten"),
                new Leerling("Marcel", "Willems"),
                new Leerling("Rosa", "Hoekstra"),
                new Leerling("Bob", "Koster"),
                new Leerling("Sasha", "Verhoeven"),
                new Leerling("Thijmen", "Prins"),
                new Leerling("Sam", "Leunens"),
                new Leerling("Sarah", "VanBossche"),
                new Leerling("Femke", "Vanhoeke"),
                new Leerling("Sep", "Jacobs")));

        List<Klas> klassen = new ArrayList<>(Arrays.asList(
                new Klas("NaamKlas1", leerlingen),
                new Klas("NaamKlas2", leerlingen.subList(1, 17)),
                new Klas("NaamKlas3", leerlingen.subList(2, 19))
        ));

        List<Actie> acties = new ArrayList<>(Arrays.asList( // geen loop van maken!
                new Actie("Actie1"),
                new Actie("Actie2"),
                new Actie("Actie3"),
                new Actie("Actie4"),
                new Actie("Actie5"),
                new Actie("Actie6"),
                new Actie("Actie7"),
                new Actie("Actie8"),
                new Actie("Actie9"),
                new Actie("Actie10"),
                new Actie("Actie11"),
                new Actie("Actie12")
        ));
        File opgave1 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Optelsommen_Opgave.pdf");
        File feedback1 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Optelsommen_Feedback.pdf");
        File opgave2 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Vermenigvuldigingen_Opgave.pdf");
        File feedback2 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Vermenigvuldigingen_Feedback.pdf");
        File opgave3 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Hoofdstad VK_Opgave.pdf");
        File feedback3 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Hoofdstad VK_Feedback.pdf");

        List<Oefening> oefeningen = new ArrayList<>(Arrays.asList(
                new Oefening("Optelsommen", "40", vakken.get(2), opgave1, feedback1, bewerkingenDatabankLijst.subList(8, 12), doelstellingenArray.subList(0, 3)),
                new Oefening("Vermenigvuldigingen", "70", vakken.get(1), opgave2, feedback2, bewerkingenDatabankLijst.subList(0, 7), doelstellingenArray.subList(6, 10)),
                new Oefening("Hoofdstad VK", "Londen", vakken.get(4), opgave3, feedback3, bewerkingenDatabankLijst.subList(3, 10), doelstellingenArray.subList(5, 7))
        ));

        List<Box> boxen = new ArrayList<>(Arrays.asList( // geen loop van maken!
                new Box("box1", "Boxomschrijving1", vakken.get(2), acties.subList(2, 8), oefeningen.subList(0, 2)),
                new Box("box2", "Boxomschrijving3", vakken.get(0), acties.subList(0, 11), oefeningen.subList(1, 3)),
                new Box("box3", "Boxomschrijving2", vakken.get(3), acties.subList(0, 4), oefeningen.subList(0, 1))
        ));

        bb.add(boxen.get(0));
        bb.add(boxen.get(1));
        bb.add(boxen.get(2));


        GenericDaoJpa klasDao = new GenericDaoJpa<>(Klas.class);
        klassen.forEach(klas -> {
            klasDao.insert(klas);
        });

        GenericDaoJpa ActieDao = new GenericDaoJpa<>(Actie.class);
        acties.forEach(actie -> {
            ActieDao.insert(actie);
        });

        vakken.forEach(vak -> ob.addVak(vak));
        bewerkingenDatabankLijst.forEach(bw -> ob.addGroepsbewerking(bw));
        doelstellingenArray.forEach(dls -> ob.addDoelstelling(dls));
        /* ob.add(oefeningen.get(0));
        ob.add(oefeningen.get(1));
        ob.add(oefeningen.get(2));
*/
        /* GenericDaoJpa boxDao = new GenericDaoJpa<>(Box.class);
        boxen.forEach(box -> {
            boxDao.insert(box);
        });*/

        


        // public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback, List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen) {

        /*GenericDaoJpa bewerkingDoa = new GenericDaoJpa(Groepsbewerking.class);
        GenericDaoJpa doelstellingDoa = new GenericDaoJpa(String.class);
        GenericDaoJpa vakDoa = new GenericDaoJpa(Vak.class);
        bewerkingenDatabankLijst.forEach(bew -> bewerkingDoa.insert(bew));
        vakken.forEach(vak -> {
            vakDoa.insert(vak);
        });
        doelstellingenArray.forEach(doel -> doelstellingDoa.insert(doel));*/
        //public Oefening(String naam, String antwoord, Vak vak, File opgave, File feedback, Set<Groepsbewerking> groepsbewerkingen, List<String> doelstellingen) {
        //ob.addOefening(new Oefening("Hoofdstad VK", "London", new Vak("Aardrijkskunde")));
        //ob.addOefening(new Oefening("Optelsommen", "1", new Vak("Wiskunde")));
    }
}
