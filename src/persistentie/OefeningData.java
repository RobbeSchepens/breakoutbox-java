package persistentie;

import domein.Oefening;
import domein.OefeningBeheerder;
import domein.Vak;

public class OefeningData {
    private final OefeningBeheerder ob;

    public OefeningData(OefeningBeheerder oefeningBeheerder) {
        ob = oefeningBeheerder;
    }

    public void populeerData() {
        ob.addOefening(new Oefening("Hoofdstad VK", "London", new Vak("Aardrijkskunde")));
        ob.addOefening(new Oefening("Optelsommen", "1", new Vak("Wiskunde")));
    }
}
