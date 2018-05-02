package domein;

import java.util.HashSet;
import java.util.Set;

public final class SessieBeheerder implements BeheerderSubject, BeheerderObserver {

    private Set<BeheerderObserver> observers;
    
    public SessieBeheerder() {
        observers = new HashSet<>();
    }

    /*public void checkOpDubbel(Sessie o) {
        for (IActie item : getSessies()) {
            if (item.getNaam().equals(o.getNaam())) {
                throw new IllegalArgumentException("Deze naam is al in gebruik");
            }
        }
    }*/

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