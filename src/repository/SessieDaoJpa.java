package repository;

import domein.Sessie;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public class SessieDaoJpa extends GenericDaoJpa<Sessie> implements SessieDao {

    public SessieDaoJpa() {
        super(Sessie.class);
    }

    @Override
    public Sessie getSessieByCode(String sessiecode) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Bier.findByCode", Sessie.class)
                    .setParameter("code", sessiecode)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
