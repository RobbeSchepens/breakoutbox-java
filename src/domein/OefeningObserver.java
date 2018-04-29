package domein;

import java.util.List;

public interface OefeningObserver {
    public void update(IOefening oefening);
    public void update(List<Groepsbewerking> groepsbewerkingen, List<Doelstelling> doelstellingen);
}