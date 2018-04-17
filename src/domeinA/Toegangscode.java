package domeinA;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "toegangscodes")
public class Toegangscode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toegangscodeId;
    public String code;

    protected Toegangscode() {
    }

    public Toegangscode(String code) {
        this.code = code;
    }
}