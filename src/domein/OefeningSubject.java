package domein;

public interface OefeningSubject {
    void addOefeningObserver(OefeningObserver o);
    void removeOefeningObserver(OefeningObserver o);
}