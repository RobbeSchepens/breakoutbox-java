package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.GenericDaoJpa;

public class KlasBeheerder {
    
    //Repositories
    private GenericDaoJpa<Klas> klasRepo;

    // Lijsten
    private ObservableList<? extends IKlas> klassen;
    
    public KlasBeheerder() {
        setKlasRepo(new GenericDaoJpa(Klas.class));
        System.out.println(klasRepo.findAll());
        // Seeden van database
        //OefeningData od = new OefeningData(this);
    }

    public void setKlasRepo(GenericDaoJpa<Klas> mock) {
        klasRepo = mock;
    }
    
    public ObservableList<? extends IKlas> getKlassen() {
        if (klassen == null)
            klassen = FXCollections.observableArrayList(klasRepo.findAll());

        return klassen;
    }
    
    public void add(Klas o) {
        klasRepo.startTransaction();
        ((ObservableList<Klas>)getKlassen()).add(o);
        klasRepo.insert(o);
        klasRepo.commitTransaction();
    }

    public void update(Klas o) {
        klasRepo.startTransaction();
        klasRepo.update(o);
        klasRepo.commitTransaction();
    }
    
    public void delete(Klas o) {
        klasRepo.startTransaction();
        klasRepo.delete(o);
        getKlassen().remove(o);
        klasRepo.commitTransaction();
    }
}
