/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Chris
 */
public class Util {
    
    public static Stage showModalWindow(String fxmlFileName, Button btnSrc, String title) {
        
        Stage window = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Util.class.getResource("/main/resources/view/" + fxmlFileName));
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An error has occurred in " + e.getClass().getName() + ".\n" + e.getMessage());
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(sw.toString())));
            alert.showAndWait();
        }
        window.setScene(new Scene(root));
        window.setTitle(title);
        window.setResizable(false);
        window.initOwner(btnSrc.getScene().getWindow());
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
        return window;
    }
    
   
    
}
