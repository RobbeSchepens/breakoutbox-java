/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class HoofdMenuController extends AnchorPane { //implements Initializable

    public HoofdMenuController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HoofdMenu.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
