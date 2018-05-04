package domein;

public interface ActieSubject {
    void addActieObserver(ActieObserver o);
    void removeActieObserver(ActieObserver o);
}
