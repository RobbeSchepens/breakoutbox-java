package repository;

import domeinold.Sessie;
import javax.persistence.EntityNotFoundException;

public interface SessieDao extends GenericDao<Sessie> {
    public Sessie getSessieByCode(String sessiecode) throws EntityNotFoundException;
    public Sessie getSessieByName(String sessienaam) throws EntityNotFoundException;
}
