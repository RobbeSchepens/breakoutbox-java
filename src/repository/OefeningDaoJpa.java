package repository;

import domein.Oefening;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import static repository.GenericDaoJpa.em;

public class OefeningDaoJpa extends GenericDaoJpa<Oefening> implements OefeningDao {

    public OefeningDaoJpa() {
        super(Oefening.class);
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
