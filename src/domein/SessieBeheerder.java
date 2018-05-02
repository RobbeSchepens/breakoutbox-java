package domein;

import java.util.HashSet;
import java.util.Set;

public final class SessieBeheerder implements BeheerderSubject, BeheerderObserver {

    private Set<BeheerderObserver> observers;
    
    public SessieBeheerder() {
        observers = new HashSet<>();
    }

    @Override
    public void addBeheerderObserver(BeheerderObserver o) {
        if (!observers.contains(o))
            observers.add(o);
    }

    @Override
    public void removeBeheerderObserver(BeheerderObserver o) {
        observers.remove(o);
    }
    
    private void notifyObservers() {
        observers.forEach(e -> e.updateSessies());
    }

    @Override public void updateOefeningen() {}
    @Override public void updateBoxes() {
        //boxes = null;
    }
    @Override public void updateSessies() {}
    @Override public void updateActies() {}
    @Override public void updateKlassen() {
        //klassen = null;
    }
}