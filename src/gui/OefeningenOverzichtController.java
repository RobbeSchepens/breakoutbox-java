/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.OefeningController;
import domein.Vak;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Daan
 */
public class OefeningenOverzichtController extends AnchorPane {
//doelstellingen ??
    //bewerkingen??

    private OefeningController dc;
    private List<Vak> vakkenlijst;
    private FileChooser opgaveChooser;
    private FileChooser feedbackChooser;
    private File opgave;
    private File feedback;

    public OefeningenOverzichtController(OefeningController dc) {

    }
}
