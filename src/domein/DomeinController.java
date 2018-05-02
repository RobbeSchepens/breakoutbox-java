package domein;

public class DomeinController {
    
    private OefeningController oc;
    private BoxController bc;
    private KlasController kc;
    private ActieController ac;
    private SessieController sc;

    public DomeinController() {
        this.oc = new OefeningController();
        this.bc = new BoxController();
        this.kc = new KlasController();
        this.ac = new ActieController();
        this.sc = new SessieController();
        
        // Observer pattern
    }

    public OefeningController getOc() {
        return oc;
    }

    public BoxController getBc() {
        return bc;
    }

    public KlasController getKc() {
        return kc;
    }

    public ActieController getAc() {
        return ac;
    }

    public SessieController getSc() {
        return sc;
    }
}
