package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Groepsbewerking<T> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    // Op deze klasse Strategy Pattern toepassen en 
    // krijgt dan niet-generieke subklassen voor de bewerking concreet te maken voor double/string
    // zoals: Rotate, Switch, Add, Multiply
    
}
