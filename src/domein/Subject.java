package domein;

public interface Subject {
    void addObserver(OefeningObserver o);
    void removeObserver(OefeningObserver o);
}