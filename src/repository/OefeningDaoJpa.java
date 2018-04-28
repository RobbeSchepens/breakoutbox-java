package repository;

import domein.Oefening;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public class OefeningDaoJpa extends GenericDaoJpa<Oefening> implements OefeningDao {

    public OefeningDaoJpa() {
        super(Oefening.class);
    }
    
    @Override
    public void closePersistency() {
        super.closePersistency();
    }
    @Override
    public void startTransaction() {
        super.startTransaction();
    }
    @Override
    public void commitTransaction() {
        super.commitTransaction();
    }
    @Override
    public void rollbackTransaction() {
        super.rollbackTransaction();
    }

    @Override
    public Oefening getOefeningByName(String oefeningnaam) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Oefening.findByName", Oefening.class)
                    .setParameter("oefeningnaam", oefeningnaam)
                    .getSingleResult();
        } catch (NoResultException ex) {
            //throw new EntityNotFoundException();
            return null;
        }
    }

}
