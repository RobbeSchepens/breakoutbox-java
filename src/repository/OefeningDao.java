package repository;

import domein.Oefening;
import javax.persistence.EntityNotFoundException;

public interface OefeningDao extends GenericDao<Oefening> {
    public Oefening getOefeningByName(String naam) throws EntityNotFoundException;
}