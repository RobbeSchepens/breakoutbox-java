package domein;

public class SessieController {

    //private Sessie huidigeSessie;
    private SessieBeheerder sb;

    public SessieController() {
        this.sb = new SessieBeheerder();
    }

    public SessieBeheerder getSb() {
        return sb;
    }
    
}