package domein;

public interface UpdateItemTableSubject {
    void addUpdatedItemObserver(UpdateItemTableObserver o);
    void removeUpdatedItemObserver(UpdateItemTableObserver o);
}
