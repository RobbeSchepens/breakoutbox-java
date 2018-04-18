package repository;

import domein.Klas;
import javax.persistence.EntityNotFoundException;

public interface KlasDao extends GenericDao<Klas> {
    public Klas getKlasById(int id) throws EntityNotFoundException;
}