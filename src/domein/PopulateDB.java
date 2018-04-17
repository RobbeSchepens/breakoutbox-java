package domein;

import domein.Sessie;
import repository.GenericDaoJpa;

public class PopulateDB {
    public void run() {
        GenericDaoJpa sessiedao = new GenericDaoJpa(Sessie.class);
        sessiedao.startTransaction();
        sessiedao.insert(new Sessie("ABC"));
        sessiedao.insert(new Sessie("X9Y3"));

        sessiedao.commitTransaction();
    }
}