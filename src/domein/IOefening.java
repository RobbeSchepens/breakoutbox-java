package domein;

import java.util.List;
import javafx.beans.property.StringProperty;

public interface IOefening {
    String getNaam();
    String getAntwoord();
    StringProperty naamProperty();
    PDF getOpgave();
    PDF getFeedback();
    Vak getVak();
    List<Groepsbewerking> getGroepsBewerkingen();
    List<Doelstelling> getDoelstellingen();
}