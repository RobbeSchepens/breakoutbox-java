package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import repository.OefeningDao;
import repository.OefeningDaoJpa;
import repository.PopulateDB;

public class OefeningController {
    private List<Oefening> oefeningenLijst;
    private OefeningDao oefeningRepo;

    public OefeningController() {
        this(true);
    }
    
    public OefeningController(boolean withInit) {
        if (withInit) {
            new PopulateDB().run();
        }
        setOefeningRepo(new OefeningDaoJpa());
    }

    public void setOefeningRepo(OefeningDao mock){
        oefeningRepo = mock;
    }

    public List<String> geefOefeningLijst(){
        return getOefeningList().stream().map(Oefening::toString).collect(Collectors.toList());
    }
    
    private List<Oefening> getOefeningList() {
        if (oefeningenLijst == null) {
            oefeningenLijst = oefeningRepo.findAll();
        }
        return oefeningenLijst;
    }
}
