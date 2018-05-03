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
import domein.Sessie;
import domein.SubstractGroepsbewerking;
import domein.Vak;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import repository.GenericDaoJpa;

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
        for (double i = 1; i < 10; i++) { // 20 bewerkingen maken
            bewerkingenDatabankLijst.add(new AddGroepsbewerking("Voeg toe", i));
            bewerkingenDatabankLijst.add(new SubstractGroepsbewerking("Trek af", i));
        }
        GenericDaoJpa gbwDao = new GenericDaoJpa<>(Groepsbewerking.class);
        bewerkingenDatabankLijst.forEach(gbw -> {
            gbwDao.startTransaction();
            gbwDao.insert(gbw);
            gbwDao.commitTransaction();
        });

        List<Doelstelling> doelstellingenArray = new ArrayList(Arrays.asList(
                new Doelstelling("WISK107", "doelstelling"),
                new Doelstelling("WISK70", "doelstelling"),
                new Doelstelling("WISK11", "doelstelling"),
                new Doelstelling("NLD19", "doelstelling"),
                new Doelstelling("NLD101", "doelstelling"),
                new Doelstelling("NLD89", "doelstelling"),
                new Doelstelling("NAT91", "doelstelling"),
                new Doelstelling("NAT5", "doelstelling"),
                new Doelstelling("NAT27", "doelstelling"),
                new Doelstelling("GESCH99", "doelstelling"),
                new Doelstelling("GESCH12", "doelstelling"),
                new Doelstelling("GESCH52", "doelstelling"),
                new Doelstelling("AARD2", "doelstelling"),
                new Doelstelling("AARD23", "doelstelling"),
                new Doelstelling("AARD98", "doelstelling")
        ));

        GenericDaoJpa doelsDao = new GenericDaoJpa<>(Doelstelling.class);
        doelstellingenArray.forEach(doels -> {
            doelsDao.startTransaction();
            doelsDao.insert(doels);
            doelsDao.commitTransaction();
        });
        List<Actie> acties = new ArrayList<>(Arrays.asList( // geen loop van maken!
                new Actie("Ga naar de westelijke hoek van de klas"),
                new Actie("Pak de ballon"),
                new Actie("Pak de doos"),
                new Actie("Ga naar de hoek me"),
                new Actie("Vind de envelop"),
                new Actie("Zoek in de atlas"),
                new Actie("Zoek de glazen pot"),
                new Actie("Vraag aan de leerkracht"),
                new Actie("Pak de doos"),
                new Actie("Zoek de pen"),
                new Actie("Kijk op de computer"),
                new Actie("Kijk onder de tafel")
        ));
        GenericDaoJpa actieDao = new GenericDaoJpa<>(Actie.class);
        acties.forEach(actie -> {
            actieDao.startTransaction();
            actieDao.insert(actie);
            actieDao.commitTransaction();
        });

        List<Vak> vakken = new ArrayList();
        vakken.add(new Vak("Aardrijkskunde"));
        vakken.add(new Vak("Wiskunde"));
        vakken.add(new Vak("Geschiedenis"));
        vakken.add(new Vak("Natuurkunde"));
        vakken.add(new Vak("Nederlands"));

        GenericDaoJpa vakDao = new GenericDaoJpa<>(Vak.class);
        vakken.forEach(vk -> {
            vakDao.startTransaction();
            vakDao.insert(vk);
            vakDao.commitTransaction();
        });

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

        GenericDaoJpa llnDao = new GenericDaoJpa<>(Leerling.class);
        leerlingen.forEach(ll -> {
            llnDao.startTransaction();
            llnDao.insert(ll);
            llnDao.commitTransaction();

        });

        List<Klas> klassen = new ArrayList<>(Arrays.asList(
                new Klas("2E3", new ArrayList<Leerling>(Arrays.asList(
                        (Leerling) llnDao.get(1L),
                        (Leerling) llnDao.get(2L),
                        (Leerling) llnDao.get(3L),
                        (Leerling) llnDao.get(4L)
                ))),
                new Klas("1A4", new ArrayList<Leerling>(Arrays.asList(
                        (Leerling) llnDao.get(10L),
                        (Leerling) llnDao.get(11L),
                        (Leerling) llnDao.get(12L),
                        (Leerling) llnDao.get(13L),
                        (Leerling) llnDao.get(14L),
                        (Leerling) llnDao.get(17L),
                        (Leerling) llnDao.get(1L),
                        (Leerling) llnDao.get(2L),
                        (Leerling) llnDao.get(3L),
                        (Leerling) llnDao.get(4L),
                        (Leerling) llnDao.get(5L), (Leerling) llnDao.get(6L),
                        (Leerling) llnDao.get(7L), (Leerling) llnDao.get(8L),
                        (Leerling) llnDao.get(9L)
                ))),
                new Klas("2A1", new ArrayList<Leerling>(Arrays.asList(
                        (Leerling) llnDao.get(2L),
                        (Leerling) llnDao.get(3L),
                        (Leerling) llnDao.get(4L),
                        (Leerling) llnDao.get(14L), (Leerling) llnDao.get(8L),
                        (Leerling) llnDao.get(15L), (Leerling) llnDao.get(19L),
                        (Leerling) llnDao.get(16L), (Leerling) llnDao.get(20L),
                        (Leerling) llnDao.get(17L)
                )))
        ));

        GenericDaoJpa klasDao = new GenericDaoJpa<>(Klas.class);
        klassen.forEach(klas -> {
            klasDao.startTransaction();
            klasDao.insert(klas);
            klasDao.commitTransaction();
        });

        File opgave1 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Optelsommen_Opgave.pdf");
        File feedback1 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Optelsommen_Feedback.pdf");
        File opgave2 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Vermenigvuldigingen_Opgave.pdf");
        File feedback2 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Vermenigvuldigingen_Feedback.pdf");

        File opgave3 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Hoofdstad VK_Opgave.pdf");
        File feedback3 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Hoofdstad VK_Feedback.pdf");
        File opgave4 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Delingen_Opgave.pdf");
        File feedback4 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Delingen_Feedback.pdf");

        File opgave5 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Dieren_Opgave.pdf");
        File feedback5 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Dieren_Feedback.pdf");
        File opgave6 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Kleuren_Opgave.pdf");
        File feedback6 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Kleuren_Feedback.pdf");

        File opgave7 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Letters_Opgave.pdf");
        File feedback7 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Letters_Feedback.pdf");
        File opgave8 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Organen_Opgave.pdf");
        File feedback8 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Organen_Feedback.pdf");

        File opgave9 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Rekensommen_Opgave.pdf");
        File feedback9 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Rekensommen_Feedback.pdf");
        File opgave10 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Aftrekkingen_Opgave.pdf");
        File feedback10 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Aftrekkingen_Feedback.pdf");

        File opgave11 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Voltooid deelwoorden_Opgave.pdf");
        File feedback11 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Voltooid deelwoorden_Feedback.pdf");
        File opgave12 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Werkwoorden_Opgave.pdf");
        File feedback12 = new File(System.getProperty("user.dir") + File.separator + "PDFinit" + File.separator + "Werkwoorden_Feedback.pdf");

        /*GenericDaoJpa pdfDao = new GenericDaoJpa<>(PDF.class);
        klassen.forEach(pdf -> {
            klasDao.startTransaction();
            klasDao.insert(pdf);
            klasDao.commitTransaction();
        });*/
        List<Oefening> oefeningen = new ArrayList<>(Arrays.asList(
                new Oefening("Optelsommen", "40", (Vak) vakDao.get(1L), opgave1, feedback1, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L),
                        (Groepsbewerking) gbwDao.get(4L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(6L),
                        (Groepsbewerking) gbwDao.get(7L),
                        (Groepsbewerking) gbwDao.get(8L),
                        (Groepsbewerking) gbwDao.get(9L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(1L),
                        (Doelstelling) doelsDao.get(2L),
                        (Doelstelling) doelsDao.get(3L)
                ))), new Oefening("Vermenigvuldigingen", "542", vakken.get(1), opgave2, feedback2, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L),
                        (Groepsbewerking) gbwDao.get(4L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(6L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(1L),
                        (Doelstelling) doelsDao.get(2L),
                        (Doelstelling) doelsDao.get(3L)
                ))),
                new Oefening("Hoofdstad VK", "Londen", vakken.get(1), opgave3, feedback3, bewerkingenDatabankLijst.subList(3, 9), doelstellingenArray.subList(2, 8)),
                new Oefening("Delingen", "23", vakken.get(1), opgave4, feedback4, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L),
                        (Groepsbewerking) gbwDao.get(7L),
                        (Groepsbewerking) gbwDao.get(10L),
                        (Groepsbewerking) gbwDao.get(8L),
                        (Groepsbewerking) gbwDao.get(9L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(6L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(4L),
                        (Doelstelling) doelsDao.get(5L),
                        (Doelstelling) doelsDao.get(6L)
                ))),
                new Oefening("Dieren", "Aap", vakken.get(3), opgave5, feedback5, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L),
                        (Groepsbewerking) gbwDao.get(4L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(6L),
                        (Groepsbewerking) gbwDao.get(7L),
                        (Groepsbewerking) gbwDao.get(8L),
                        (Groepsbewerking) gbwDao.get(9L),
                        (Groepsbewerking) gbwDao.get(10L),
                        (Groepsbewerking) gbwDao.get(11L),
                        (Groepsbewerking) gbwDao.get(12L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(7L),
                        (Doelstelling) doelsDao.get(8L)
                ))),
                new Oefening("Kleuren", "Rood", vakken.get(4), opgave6, feedback6, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L),
                        (Groepsbewerking) gbwDao.get(4L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(6L),
                        (Groepsbewerking) gbwDao.get(12L),
                        (Groepsbewerking) gbwDao.get(13L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(1L),
                        (Doelstelling) doelsDao.get(3L),
                        (Doelstelling) doelsDao.get(4L),
                        (Doelstelling) doelsDao.get(2L)
                ))),
                new Oefening("Letters", "HYU", vakken.get(4), opgave7, feedback7, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(1L),
                        (Doelstelling) doelsDao.get(2L),
                        (Doelstelling) doelsDao.get(3L),
                        (Doelstelling) doelsDao.get(4L),
                        (Doelstelling) doelsDao.get(5L),
                        (Doelstelling) doelsDao.get(6L)
                ))),
                new Oefening("Organen", "Longen", vakken.get(3), opgave8, feedback8, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L),
                        (Groepsbewerking) gbwDao.get(4L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(6L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(7L),
                        (Doelstelling) doelsDao.get(5L)
                ))),
                new Oefening("Rekensommen", "800", vakken.get(1), opgave9, feedback9, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(8L),
                        (Groepsbewerking) gbwDao.get(9L),
                        (Groepsbewerking) gbwDao.get(10L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(1L),
                        (Doelstelling) doelsDao.get(2L),
                        (Doelstelling) doelsDao.get(3L),
                        (Doelstelling) doelsDao.get(4L),
                        (Doelstelling) doelsDao.get(8L),
                        (Doelstelling) doelsDao.get(9L)
                ))),
                new Oefening("Aftrekkingen", "80", vakken.get(1), opgave10, feedback10, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(4L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(6L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(2L),
                        (Doelstelling) doelsDao.get(3L)
                ))),
                new Oefening("Vortooid deelwoorden", "Ik heb gemist", vakken.get(4), opgave11, feedback11, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(5L)))),
                new Oefening("Werkwoorden", "babbelen", vakken.get(4), opgave12, feedback12, new ArrayList<Groepsbewerking>(Arrays.asList(
                        (Groepsbewerking) gbwDao.get(1L),
                        (Groepsbewerking) gbwDao.get(2L),
                        (Groepsbewerking) gbwDao.get(3L),
                        (Groepsbewerking) gbwDao.get(4L),
                        (Groepsbewerking) gbwDao.get(11L),
                        (Groepsbewerking) gbwDao.get(12L),
                        (Groepsbewerking) gbwDao.get(5L),
                        (Groepsbewerking) gbwDao.get(14L),
                        (Groepsbewerking) gbwDao.get(15L),
                        (Groepsbewerking) gbwDao.get(16L),
                        (Groepsbewerking) gbwDao.get(6L)
                )), new ArrayList<Doelstelling>(Arrays.asList(
                        (Doelstelling) doelsDao.get(4L)
                )))
        ));

        GenericDaoJpa oDao = new GenericDaoJpa<>(Oefening.class);
        oefeningen.forEach(oef -> {

            oDao.startTransaction();
            oDao.insert(oef);
            oDao.commitTransaction();
        });

        List<Box> boxen = new ArrayList<>(Arrays.asList( // geen loop van maken!
                new Box("Wiskunde", "Foxus op wiskunde", (Vak) vakDao.get(2L), new ArrayList<Actie>(Arrays.asList(
                        (Actie) actieDao.get(5L),
                        (Actie) actieDao.get(6L),
                        (Actie) actieDao.get(7L),
                        (Actie) actieDao.get(8L),
                        (Actie) actieDao.get(9L),
                        (Actie) actieDao.get(10L)
                )), new ArrayList<Oefening>(Arrays.asList(
                        (Oefening) oDao.get(1L),
                        (Oefening) oDao.get(2L),
                        (Oefening) oDao.get(4L),
                        (Oefening) oDao.get(12L),
                        (Oefening) oDao.get(7L),
                        (Oefening) oDao.get(6L),
                        (Oefening) oDao.get(10L),
                        (Oefening) oDao.get(9L)
                ))),
                new Box("NL", "focus op nederlands", (Vak) vakDao.get(5L), new ArrayList<Actie>(Arrays.asList(
                        (Actie) actieDao.get(11L),
                        (Actie) actieDao.get(10L),
                        (Actie) actieDao.get(9L),
                        (Actie) actieDao.get(8L),
                        (Actie) actieDao.get(7L),
                        (Actie) actieDao.get(6L), (Actie) actieDao.get(4L),
                        (Actie) actieDao.get(5L), (Actie) actieDao.get(3L)
                )), new ArrayList<Oefening>(Arrays.asList(
                        (Oefening) oDao.get(7L),
                        (Oefening) oDao.get(11L),
                        (Oefening) oDao.get(3L),
                        (Oefening) oDao.get(12L),
                        (Oefening) oDao.get(6L)
                ))),
                new Box("Van alles wat", "Van alles wat", (Vak) vakDao.get(1L), new ArrayList<Actie>(Arrays.asList(
                        (Actie) actieDao.get(1L),
                        (Actie) actieDao.get(2L),
                        (Actie) actieDao.get(3L),
                        (Actie) actieDao.get(4L),
                        (Actie) actieDao.get(5L),
                        (Actie) actieDao.get(6L),
                        (Actie) actieDao.get(7L),
                        (Actie) actieDao.get(8L),
                        (Actie) actieDao.get(9L),
                        (Actie) actieDao.get(10L),
                        (Actie) actieDao.get(11L)
                )), new ArrayList<Oefening>(Arrays.asList(
                        (Oefening) oDao.get(1L),
                        (Oefening) oDao.get(2L),
                        (Oefening) oDao.get(3L),
                        (Oefening) oDao.get(4L),
                        (Oefening) oDao.get(5L),
                        (Oefening) oDao.get(6L),
                        (Oefening) oDao.get(7L),
                        (Oefening) oDao.get(8L),
                        (Oefening) oDao.get(9L),
                        (Oefening) oDao.get(10L),
                        (Oefening) oDao.get(11L),
                        (Oefening) oDao.get(12L)
                )))
        ));
        GenericDaoJpa boxDao = new GenericDaoJpa<>(Box.class);
        boxen.forEach(box -> {
            boxDao.startTransaction();
            boxDao.insert(box);
            boxDao.commitTransaction();
        });

        ArrayList<Sessie> lijstSessie = new ArrayList<>(Arrays.asList(
                new Sessie("Wis A1 Ma", "Een sessie wiskunde op donderdag", (Klas) klasDao.get(1L), (Box) boxDao.get(1L), false, "auto", 7, LocalDate.now()),
                new Sessie("nederlands", "Een sessie nederlands op donderdag", (Klas) klasDao.get(2L), (Box) boxDao.get(2L), false, "auto", 5, LocalDate.now())
        ));

        GenericDaoJpa sessieDao = new GenericDaoJpa<>(Sessie.class);
        lijstSessie.forEach(sessie -> {
            boxDao.startTransaction();
            boxDao.insert(sessie);
            boxDao.commitTransaction();
        });

    }
}
