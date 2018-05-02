package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Doelstelling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String code;
    private String doelstelling;

    public Doelstelling() {

    }

    public Doelstelling(String code, String doelstelling) {
        this.code = code;
        this.doelstelling = doelstelling;
    }

    public String getDoelstelling() {
        return doelstelling;
    }

    public void setDoelstelling(String doelstelling) {
        this.doelstelling = doelstelling;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return doelstelling;
    }

}