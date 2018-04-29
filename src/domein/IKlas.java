package domein;

import java.util.List;
import javafx.beans.property.StringProperty;

public interface IKlas {
    
    String getNaam();
    StringProperty naamProperty();
    List<Leerling> getLeerlingen();
    
}
