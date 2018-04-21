package domein;

public interface IOefening {
    String getNaam();
    String getAntwoord();
    PDF getOpgave();
    PDF getFeedback();
    Vak getVak();
}