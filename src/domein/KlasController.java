package domein;

public class KlasController {

    private KlasBeheerder kb = new KlasBeheerder();

    public KlasController() {
        this.kb = new KlasBeheerder();
        test();
    }

    private void test() {
        System.out.println(kb.getKlassen());
    }
    
}