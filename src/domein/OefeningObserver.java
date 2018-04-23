package domein;

import java.util.List;

public interface OefeningObserver {

    public void update(String naam, String antwoord, Vak vak, List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen);
}
