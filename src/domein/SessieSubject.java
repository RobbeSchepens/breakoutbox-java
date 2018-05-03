package domein;

public interface SessieSubject {
    void addSessieObserver(SessieObserver o);
    void removeSessieObserver(SessieObserver o);
}