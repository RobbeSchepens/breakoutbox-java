package domein;

import domein.Sessie;
import repository.GenericDaoJpa;

public class PopulateDB {
    public void run() {
        GenericDaoJpa sessiedao = new GenericDaoJpa(Sessie.class);
        sessiedao.startTransaction();
        sessiedao.insert(new Sessie("ABC", "1D Maandag", "Mix van alle oefeningen voor de klas 1D."));
        sessiedao.insert(new Sessie("X9Y3", "6E Dinsdag", "Wiskunde oefeningen voor de klas 6E."));
        sessiedao.commitTransaction();
    }
}