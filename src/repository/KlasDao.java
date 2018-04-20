package repository;

import domeinold.Klas;
import javax.persistence.EntityNotFoundException;

public interface KlasDao extends GenericDao<Klas> {
    public Klas getKlasById(int id) throws EntityNotFoundException;
}