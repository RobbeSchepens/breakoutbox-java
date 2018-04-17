package domeinA;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;
    private String omschrijving;

    public Feedback() {
    }

    public Feedback(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}