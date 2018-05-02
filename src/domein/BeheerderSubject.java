package domein;

public interface BeheerderSubject {
    void addBeheerderObserver(BeheerderObserver o);
    void removeBeheerderObserver(BeheerderObserver o);
}