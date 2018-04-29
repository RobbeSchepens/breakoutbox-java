/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import com.sun.media.sound.SoftAbstractResampler;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Daan
 */
public class KlasController {

    private KlasBeheerder kb = new KlasBeheerder();
    private List<Klas> klassenLijst;

    public KlasController() {
        this.kb = new KlasBeheerder();
        this.klassenLijst = kb.geefKlassenJPA();
        System.out.println(klassenLijst);
    }


}
