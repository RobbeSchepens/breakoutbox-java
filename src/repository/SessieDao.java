package repository;

import domein.Sessie;
import javax.persistence.EntityNotFoundException;

public interface SessieDao extends GenericDao<Sessie> {
    public Sessie getSessieByCode(String sessiecode) throws EntityNotFoundException;
}
