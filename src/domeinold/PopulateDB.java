package domeinold;

import domeinold.Groep;
import domeinold.Klas;
import domeinold.Leerkracht;
import domeinold.Leerling;
import domeinold.Sessie;
import java.util.HashSet;
import java.util.Set;
import repository.GenericDaoJpa;
import repository.KlasDaoJpa;
import repository.LeerkrachtDaoJpa;
import repository.SessieDaoJpa;

public class PopulateDB {
    public void run() {
        LeerkrachtDaoJpa leerkrachtdao = new LeerkrachtDaoJpa();
        SessieDaoJpa sessiedao = new SessieDaoJpa();
        KlasDaoJpa klasdao = new KlasDaoJpa();
        GenericDaoJpa leerlingdao = new GenericDaoJpa(Leerling.class);
        
        Leerkracht lk = new Leerkracht("Tom", "Pieters", "Tom_Pieters@school.be");
        Klas k = new Klas(lk);
        Set<Leerling> leerlingen = new HashSet<>();
        for (int i = 0; i < 8; i++)
            leerlingen.add(new Leerling("Duplo"+i, "Lego"+i));
        k.setLeerlingen(leerlingen);
        
        Sessie s1 = new Sessie("ABC", "1D Maandag", "Mix van alle oefeningen voor de klas 1D.");
        //Sessie s2 = new Sessie("X9Y3", "6E Dinsdag", "Wiskunde oefeningen voor de klas 6E.");
        
        Groep g1 = new Groep(leerlingen);
        s1.addGroep(g1);
        
        GenericDaoJpa.startTransaction();
        leerkrachtdao.insert(lk);
        
        sessiedao.insert(s1);
        //sessiedao.insert(s2);
        
        for (Leerling l : leerlingen)
            leerlingdao.insert(l);
        klasdao.insert(k);
        
        GenericDaoJpa.commitTransaction();
    }
}