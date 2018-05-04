package domein;

import java.time.LocalDate;
import javafx.beans.property.StringProperty;

public interface ISessie {
    String getNaam();
    StringProperty naamProperty();
    String getCode();
    String getOmschrijving();
    Klas getKlas();
    Box getBox();
    boolean isAfstandsonderwijs();
    String getTypeGroepen();
    int getAantalGroepen();
    LocalDate getStartdatum();
}
