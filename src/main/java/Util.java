/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.InhousePart;
import main.java.model.OutsourcedPart;

/**
 *
 * @author Chris
 */
public class Util {
    
    public static final String FXML_PATH = "/main/resources/view/";
    
    /**
     * Set up the stage and show it as a modal window.
     * @param fxmlFileName name of the fxml file to show
     * @param ownerStage the owner for this window 
     * @param title window title
     * @return a reference to the Stage object created
     */
    public static Stage showModalWindow(String fxmlFileName, Stage ownerStage, String title) {
        Stage window = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Util.class.getResource(Util.FXML_PATH + fxmlFileName));
        } catch (Exception e) {
            Util.showErrorMessage("Could not load " + fxmlFileName + ".", e);
        }
        window.setScene(new Scene(root));
        window.setTitle(title);
        window.setResizable(false);
        window.initOwner(ownerStage);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
        return window;
    }
    
    /**
     * Display an error message to the user
     * @param message Message to be displayed
     */
    public static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);  
        alert.showAndWait();
    }
    
    /**
     * Display an error message to the user, passing an exception object, and 
     * printing the stack trace in the details
     * @param message Message to be displayed
     * @param e Exception object
     */
    public static void showErrorMessage(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);        
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        alert.getDialogPane().setExpandableContent(
                new ScrollPane(new TextArea(sw.toString())));
        alert.showAndWait();
    }
        
    /**
     * Get the stage object from where an actionEvent originates.
     * @param event
     * @return the originating Stage of the event
     */
    public static Stage getStageFromActionEvent(ActionEvent event) {
        Node srcNode = (Node)event.getSource();
        return (Stage)srcNode.getScene().getWindow();
    }
    
    public static void createSampleData() {
        Main.inventory.addPart(new InhousePart(87, "Widget #1", 134.50, 5, 3, 10));
        Main.inventory.addPart(new InhousePart(87, "Widget #2", 57.25, 23, 15, 25));
        Main.inventory.addPart(new OutsourcedPart("Widget Company", "Widget #3", 98.00, 8, 5, 10));
        Main.inventory.addPart(new InhousePart(87, "Widget #4", 4.00, 45, 30, 50));
        Main.inventory.addPart(new OutsourcedPart("Acme", "Widget #5", 1238.89, 12, 10, 15));
        Main.inventory.addPart(new InhousePart(87, "Widget #6", 60.00, 10, 5, 10));
    }
    
}
