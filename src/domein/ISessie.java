package domein;

import java.time.LocalDate;
import javafx.beans.property.StringProperty;

public interface ISessie {
    String getNaam();
    StringProperty naamProperty();
    String getOmschrijving();
    LocalDate getStartdatum();
}
