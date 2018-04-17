package domein;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "leerlingen")
public class Leerling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leerlingId;
    private String voornaam;
    private String achternaam;

    protected Leerling() {
    }

    public Leerling(String voornaam, String achternaam) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }
}
