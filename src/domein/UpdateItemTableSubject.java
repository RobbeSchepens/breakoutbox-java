package domein;

public interface UpdateItemTableSubject {
    void addUpdatedItemObserver(BoxObserver o);
    void removeUpdatedItemObserver(BoxObserver o);
}
