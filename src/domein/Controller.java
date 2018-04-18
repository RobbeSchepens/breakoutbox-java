package domein;

import repository.GenericDaoJpa;

public class Controller {
    public void close() {
        GenericDaoJpa.closePersistency();
    }
}
