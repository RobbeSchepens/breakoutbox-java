package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import repository.GenericDaoJpa;

public final class BoxBeheerder {

    private GenericDaoJpa<Box> boxRepo;
    private ObservableList<? extends IBox> boxen;

    public BoxBeheerder() {
        setBoxRepo(new GenericDaoJpa(Box.class));
    }

    public GenericDaoJpa<Box> getBoxRepo() {
        return boxRepo;
    }

    public void setBoxRepo(GenericDaoJpa<Box> boxRepo) {
        this.boxRepo = boxRepo;
    }

    public ObservableList<? extends IBox> getBoxen() {
        if (boxen == null) {
            boxen = FXCollections.observableArrayList(boxRepo.findAll());
        }
        return boxen;
    }

    public void add(Box o) {
        /*if (klasRepo.getByToString(o) != null) {
            throw new IllegalArgumentException("Er bestaat al een klas met deze naam.");
        }*/

        boxRepo.startTransaction();
        ((ObservableList<Box>) getBoxen()).add(o);
        boxRepo.insert(o);
        boxRepo.commitTransaction();
    }

    public void update(Box o) {

        boxRepo.startTransaction();
        boxRepo.update(o);
        boxRepo.commitTransaction();
    }

    public void delete(Box o) {
        boxRepo.startTransaction();
        boxRepo.delete(o);
        getBoxen().remove(o);
        boxRepo.commitTransaction();
    }

}
