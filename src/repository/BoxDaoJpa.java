package repository;

import domein.Box;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import static repository.GenericDaoJpa.em;

public class BoxDaoJpa extends GenericDaoJpa<Box> implements BoxDao {

    public BoxDaoJpa() {
        super(Box.class);
    }

    @Override
    public Box getBoxById(int id) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Box.findById", Box.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
    
    @Override
    public Box getBoxByName(String boxnaam) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Box.findByName", Box.class)
                    .setParameter("boxnaam", boxnaam)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
