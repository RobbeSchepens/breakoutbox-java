package repository;

import domeinold.Box;
import javax.persistence.EntityNotFoundException;

public interface BoxDao extends GenericDao<Box> {
    public Box getBoxById(int id) throws EntityNotFoundException;
    public Box getBoxByName(String naam) throws EntityNotFoundException;
}