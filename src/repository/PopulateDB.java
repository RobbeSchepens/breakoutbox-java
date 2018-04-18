package repository;

import domein.Klas;
import domein.Leerkracht;
import domein.Leerling;
import domein.Sessie;
import java.util.HashSet;
import java.util.Set;

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
        
        GenericDaoJpa.startTransaction();
        leerkrachtdao.insert(lk);
        
        sessiedao.insert(new Sessie("ABC", "1D Maandag", "Mix van alle oefeningen voor de klas 1D."));
        sessiedao.insert(new Sessie("X9Y3", "6E Dinsdag", "Wiskunde oefeningen voor de klas 6E."));
        
        for (Leerling l : leerlingen)
            leerlingdao.insert(l);
        klasdao.insert(k);
        
        GenericDaoJpa.commitTransaction();
    }
}