package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import repository.GenericDaoJpa;
import repository.OefeningDaoJpa;

public final class BoxBeheerder {

    private GenericDaoJpa<Box> boxRepo;
    private GenericDaoJpa<Actie> actieRepo;
    private OefeningDaoJpa oefeningRepo;
    private GenericDaoJpa<Vak> vakRepo;

    private ObservableList<? extends IBox> boxen;
    private ObservableList<Vak> vakken;
    private ObservableList<? extends IOefening> oefeningen;
    private ObservableList<? extends IActie> acties;


    public BoxBeheerder() {
        setBoxRepo(new GenericDaoJpa(Box.class));
        setVakRepo(new GenericDaoJpa(Vak.class));
        setActieRepo(new GenericDaoJpa(Actie.class));
        setOefeningRepo(new OefeningDaoJpa());

    }

    public GenericDaoJpa<Box> getBoxRepo() {
        return boxRepo;
    }

    public void setBoxRepo(GenericDaoJpa<Box> boxRepo) {
        this.boxRepo = boxRepo;
    }

    public GenericDaoJpa<Actie> getActieRepo() {
        return actieRepo;
    }

    public void setActieRepo(GenericDaoJpa<Actie> actieRepo) {
        this.actieRepo = actieRepo;
    }

    public OefeningDaoJpa getOefeningRepo() {
        return oefeningRepo;
    }

    public void setOefeningRepo(OefeningDaoJpa oefeningRepo) {
        this.oefeningRepo = oefeningRepo;
    }

    public GenericDaoJpa<Vak> getVakRepo() {
        return vakRepo;
    }

    public void setVakRepo(GenericDaoJpa<Vak> vakRepo) {
        this.vakRepo = vakRepo;
    }

    public ObservableList<? extends IBox> getBoxen() {
        if (boxen == null) {
            boxen = FXCollections.observableArrayList(boxRepo.findAll());
        }
        return boxen;
    }

    public ObservableList<Vak> getVakken() {
        if (vakken == null) {
            vakken = FXCollections.observableArrayList(vakRepo.findAll());
        }
        return vakken;
    }

    public ObservableList<? extends IOefening> getOefeningen() {
        if (oefeningen == null) {
            oefeningen = FXCollections.observableArrayList(oefeningRepo.findAll());
        }
        return oefeningen;
    }

    public ObservableList<? extends IActie> getActies() {
        if (acties == null) {
            acties = FXCollections.observableArrayList(actieRepo.findAll());
        }
        return acties;
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
