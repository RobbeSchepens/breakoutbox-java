package repository;

import domein.Oefening;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import static repository.GenericDaoJpa.em;

public class OefeningDaoJpa extends GenericDaoJpa<Oefening> implements OefeningDao {

    public OefeningDaoJpa() {
        super(Oefening.class);
    }

    @Override
    public Oefening getOefeningById(int id) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Oefening.findById", Oefening.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
    
    @Override
    public Oefening getOefeningByName(String oefeningnaam) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Oefening.findByName", Oefening.class)
                    .setParameter("oefeningnaam", oefeningnaam)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
