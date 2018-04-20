package repository;

import domeinold.Sessie;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public class SessieDaoJpa extends GenericDaoJpa<Sessie> implements SessieDao {

    public SessieDaoJpa() {
        super(Sessie.class);
    }

    @Override
    public Sessie getSessieByCode(String sessiecode) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Sessie.findByCode", Sessie.class)
                    .setParameter("code", sessiecode)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
    
    @Override
    public Sessie getSessieByName(String sessienaam) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Sessie.findByCode", Sessie.class)
                    .setParameter("sessienaam", sessienaam)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
