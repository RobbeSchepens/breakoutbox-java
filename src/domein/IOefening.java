package domein;

import javafx.beans.property.StringProperty;

public interface IOefening {
    String getNaam();
    String getAntwoord();
    StringProperty naamProperty();
    PDF getOpgave();
    PDF getFeedback();
    Vak getVak();
}