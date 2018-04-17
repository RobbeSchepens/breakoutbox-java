package domein;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import repository.LeerkrachtDaoJpa;
import repository.SessieDao;
import repository.SessieDaoJpa;

public class SessieController {
    private List<Sessie> sessieLijst;
    private LeerkrachtDaoJpa leerkrachtRepo;
    private SessieDao sessieRepo;
    
    public SessieController() {
        this(true);
    }
    
    public SessieController(boolean withInit) {
        if (withInit) {
            new PopulateDB().run();
        }
        setLeerkrachtRepo(new LeerkrachtDaoJpa());
        setSessieRepo(new SessieDaoJpa());
    }
    
    public void setLeerkrachtRepo(LeerkrachtDaoJpa mock){
        leerkrachtRepo = mock;
    }

    public void setSessieRepo(SessieDao mock){
        sessieRepo = mock;
    }

    public List<String> geefSessieLijst(){
        return getSessieList().stream().map(Sessie::getCode).collect(Collectors.toList());
    }
    
    private List<Sessie> getSessieList() {
        if (sessieLijst == null) {
            sessieLijst = sessieRepo.findAll();
        }
        return sessieLijst;
    }
    
    public void voegGroepBijSessie(Groep groep, String sessiecode) throws IllegalArgumentException {
        Optional<Sessie> sessie = getSessieList().stream()
                .filter( e -> e.getCode().equalsIgnoreCase(sessiecode))
                .findFirst();
        if (!sessie.isPresent()) {
                throw new IllegalArgumentException("Sessie " + sessiecode + " komt niet voor.");
        }
        SessieDaoJpa.startTransaction();
        sessie.get().addGroep(groep);
        SessieDaoJpa.commitTransaction();
    }
    
    public List<String> geefGroepLijst(Sessie sessie) {
        Set<Groep> groepSet = sessie.getGroepSet();
        return groepSet.stream().map(Groep::toString).collect(Collectors.toList());
    }

    public void close() {
        SessieDaoJpa.closePersistency();
    }
}
