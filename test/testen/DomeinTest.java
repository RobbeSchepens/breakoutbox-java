package testen;

import domein.Groep;
import domein.Sessie;
import domein.SessieController;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GenericDao;

public class DomeinTest {
    @Mock
    private GenericDao<Sessie> sessieRepo;
    private SessieController sessieController;
    
    @Before
    public void before() {
        sessieController = new SessieController();
        MockitoAnnotations.initMocks(this);
        sessieController.setSessieRepo(sessieRepo);
    }
    
    public DomeinTest() {
    }
    @Test
    public void voegBierBijWinkel() {
       final String SESSIECODE = "X9Z3";

       Sessie eenSessie = new Sessie(SESSIECODE, "Naam", "Beschrijving.");   
       Groep eenGroep = new Groep();

       Mockito.when(sessieRepo.findAll()).thenReturn(Arrays.asList(eenSessie));
       //Mockito.when(bierRepo.getBierByName(BIERNAAM)).thenReturn(eenBier);
       
       assertFalse(eenSessie.getGroepSet().contains(eenGroep));
       sessieController.voegGroepBijSessie(eenGroep, SESSIECODE);
       assertTrue(eenSessie.getGroepSet().contains(eenGroep));
       Mockito.verify(sessieRepo).findAll();
       //Mockito.verify(bierRepo).getBierByName(BIERNAAM);
        
    }
}
