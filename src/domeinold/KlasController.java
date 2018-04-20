package domeinold;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import repository.GenericDaoJpa;
import repository.KlasDao;
import repository.KlasDaoJpa;
import repository.LeerkrachtDao;
import repository.LeerkrachtDaoJpa;

public final class KlasController {
    private List<Klas> klasLijst;
    private KlasDao klasRepo;
    private LeerkrachtDao leerkrachtRepo;
    
    public KlasController() {
        this(true);
    }
    
    public KlasController(boolean withInit) {
        if (withInit) {
            new PopulateDB().run();
        }
        setKlasRepo(new KlasDaoJpa());
        setLeerkrachtRepo(new LeerkrachtDaoJpa());
    }

    public void setKlasRepo(KlasDao mock){
        klasRepo = mock;
    }
    
    public void setLeerkrachtRepo(LeerkrachtDao mock){
        leerkrachtRepo = mock;
    }

    public List<String> geefKlasLijst(){
        return getKlasList().stream().map(Klas::toString).collect(Collectors.toList());
    }

    public List<String> geefLeerlingenVanKlasLijst(int id){
        Klas k = klasRepo.getKlasById(id);
        List<String> leerlingen = new ArrayList<>();
        for (Leerling l : k.getLeerlingen())
            leerlingen.add(l.toString());
        return leerlingen;
    }
    
    private List<Klas> getKlasList() {
        if (klasLijst == null) {
            klasLijst = klasRepo.findAll();
        }
        return klasLijst;
    }
}
