package repository;

import domein.Leerkracht;
import javax.persistence.EntityNotFoundException;

public interface LeerkrachtDao extends GenericDao<Leerkracht> {
    public Leerkracht getLeerkrachtByName(String voornaam, String achternaam) throws EntityNotFoundException;
    public Leerkracht getLeerkrachtByEmail(String email) throws EntityNotFoundException;
}
