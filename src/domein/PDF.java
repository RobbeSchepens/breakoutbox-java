package domein;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
@Access(AccessType.PROPERTY)
public class PDF implements Serializable {

    private String name;

    private String naamOef = "";
    private String typeFile = "";

    private File file;
    public static String FOLDERLOCATIE = System.getProperty("user.dir") +File.separator+ "PDFs" +File.separator;

    public PDF() {
    }

    public PDF(File file, String name, String naamOef, String typeOef) {
        this.file = file;
        this.name = name;
        this.naamOef = "_" + naamOef;
        if (typeOef.toLowerCase().equals("_feedback_")) {
            typeFile = "Feedback";
        }
        if (typeOef.toLowerCase().equals("_opgave")) {
            typeFile = "Opgave";
        }

    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    @Column
    public byte[] getDBFile() {

        byte[] fileInBytes = null;
        try {
            fileInBytes = Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileInBytes;
    }

    public void setDBFile(byte[] bytearray) {
        try {
            String path = String.format("%s%s", FOLDERLOCATIE, getName());
            Files.write(Paths.get(path), bytearray).toFile();
            this.file = new File(String.format("%s%s%s%s", FOLDERLOCATIE, getName(), naamOef, typeFile));
        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Transient
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return name;
    }
}