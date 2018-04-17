package domein;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Basic;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;
    @Basic(fetch=LAZY)
    @Lob
    private byte[] pdf;

    public Feedback() {
    }

    public Feedback(byte[] pdf) {
        this.pdf = pdf;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Arrays.hashCode(this.pdf);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Feedback other = (Feedback) obj;
        if (!Arrays.equals(this.pdf, other.pdf)) {
            return false;
        }
        return true;
    }
}