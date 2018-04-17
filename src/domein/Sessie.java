package domein;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Sessie.findByCode", query = "select s from Sessie s where s.code = :code")
})
public class Sessie implements Serializable {
    private static long serialVersionUID = 1L;
    @Id
    private String code;
    @OneToMany
    private Set<Groep> groepSet = new HashSet<>();

    public Sessie() {
    }

    public Sessie(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }

    public Set<Groep> getGroepSet() {
        return Collections.unmodifiableSet(groepSet);
    }
    
    public void addGroep(Groep groep){
        groepSet.add(groep);
    }
    
    public void removeGroep(Groep groep){
        groepSet.remove(groep);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sessie other = (Sessie) obj;
        return Objects.equals(this.code, other.code);
    }
}
