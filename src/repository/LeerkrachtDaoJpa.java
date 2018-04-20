package repository;

import domeinold.Leerkracht;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public class LeerkrachtDaoJpa extends GenericDaoJpa<Leerkracht> implements LeerkrachtDao {

    public LeerkrachtDaoJpa() {
        super(Leerkracht.class);
    }

    @Override
    public Leerkracht getLeerkrachtByName(String voornaam, String achternaam) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Leerkracht.findByName", Leerkracht.class)
                    .setParameter("voornaam", voornaam)
                    .setParameter("achternaam", achternaam)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Leerkracht getLeerkrachtByEmail(String email) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Leerkracht.findByEmail", Leerkracht.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
