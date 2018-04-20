package repository;

import domeinold.Klas;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public class KlasDaoJpa extends GenericDaoJpa<Klas> implements KlasDao {

    public KlasDaoJpa() {
        super(Klas.class);
    }

    @Override
    public Klas getKlasById(int id) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Klas.findById", Klas.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
