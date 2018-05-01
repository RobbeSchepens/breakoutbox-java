package domein;

public interface BoxSubject {
    void addBoxObserver(BoxObserver o);
    void removeBoxObserver(BoxObserver o);
}
