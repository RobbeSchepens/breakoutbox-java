package testen;

import domein.Groep;
import domein.Sessie;
import domein.SessieController;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.SessieDao;

public class DomeinTest {
    @Mock
    private SessieDao sessieRepo;
    private SessieController sessieController;
    
    @Before
    public void before() {
        sessieController = new SessieController();
        sessieRepo = Mockito.mock(SessieDao.class);
        sessieController.setSessieRepo(sessieRepo);
    }
    
    public DomeinTest() {
    }
    
    @Test
    public void voegGroepBijSessie() {
       final String SESSIECODE = "X9Z3";

       Sessie eenSessie = new Sessie(SESSIECODE, "Naam", "Beschrijving.");   
       Groep eenGroep = new Groep();

       Mockito.when(sessieRepo.findAll()).thenReturn(Arrays.asList(eenSessie));
       Mockito.when(sessieRepo.getSessieByCode(SESSIECODE)).thenReturn(eenSessie);
       
       assertFalse(eenSessie.getGroepSet().contains(eenGroep));
       sessieController.voegGroepBijSessie(eenGroep, SESSIECODE);
       assertTrue(eenSessie.getGroepSet().contains(eenGroep));
       Mockito.verify(sessieRepo).findAll();
       Mockito.verify(sessieRepo).getSessieByCode(SESSIECODE);
    }
}
