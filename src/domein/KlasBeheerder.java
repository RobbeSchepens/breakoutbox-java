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
        /*if (klasRepo.getByToString(o) != null) {
            throw new IllegalArgumentException("Er bestaat al een klas met deze naam.");
        }*/
        System.out.println(o.getLeerlingen());
        klasRepo.startTransaction();
        ((ObservableList<Klas>)getKlassen()).add(o);
        klasRepo.insert(o);
        klasRepo.commitTransaction();
    }

    public void update(Klas o) {

        System.out.println(o.getLeerlingen());
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
