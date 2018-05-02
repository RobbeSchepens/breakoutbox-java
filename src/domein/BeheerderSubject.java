package domein;

public interface BeheerderSubject {
    void addBeheerderObserver(OefeningObserver o);
    void removeBeheerderObserver(OefeningObserver o);
}