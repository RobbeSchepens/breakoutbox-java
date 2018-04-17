package domein;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@Table(name = "leerkrachten")
@NamedQueries({
    @NamedQuery(name = "Leerkracht.findByName", 
            query = "select lk from Leerkracht lk where lk.voornaam = :voornaam and lk.achternaam = :achternaam"),
    @NamedQuery(name = "Leerkracht.findByEmail", 
            query = "select lk from Leerkracht lk where lk.email = :email")
})
public class Leerkracht implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leerkrachtId;
    private String voornaam;
    private String achternaam;
    private String email;
    @OneToMany
    private Set<Sessie> sessies;
    @OneToMany
    private Set<Klas> klassen;

    protected Leerkracht() {
    }

    public Leerkracht(String voornaam, String achternaam, String email, Set<Klas> klassen) {
        klassen = klassen;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }

    public void addSessie(Sessie s) {
        sessies.add(s);
    }
}