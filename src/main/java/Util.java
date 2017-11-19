/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.java.model.InhousePart;
import main.java.model.OutsourcedPart;
import main.java.model.Product;

/**
 *
 * @author Chris
 */
public class Util {
    
    public static final String FXML_PATH = "/main/resources/view/";
    
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
    
    public static <T> void setCurrencyFormattingOnTableColumn(TableColumn col) {  
        col.setCellFactory(column -> {            
           return new TableCell<T, Double>() {
               @Override
               protected void updateItem(Double item, boolean empty) {
                   super.updateItem(item, empty);
                   if(item == null || empty) {
                       setText(null);
                   } else {
                       setText(NumberFormat.getCurrencyInstance().format(item));
                   }
               }
           }; 
        });
    }
    
    public static void createSampleData() {
        //load sample part data into inventory
        Main.inventory.addPart(new InhousePart(87, "Widget #1", 134.50, 5, 3, 10));
        Main.inventory.addPart(new InhousePart(67, "Widget #2", 57.25, 23, 15, 25));
        Main.inventory.addPart(new OutsourcedPart("Widget Company", "Widget #3", 98.00, 8, 5, 10));
        Main.inventory.addPart(new InhousePart(54, "Widget #4", 4.00, 45, 30, 50));
        Main.inventory.addPart(new OutsourcedPart("Acme", "Widget #5", 1238.89, 12, 10, 15));
        Main.inventory.addPart(new InhousePart(2, "Widget #6", 60.00, 10, 5, 10));
        
        //load sample product data into inventory
        Product prod1 = new Product("Motor Assembly", 2500.00, 5, 2, 8);
        Product prod2 = new Product("Fan Assembly", 500.00, 5, 2, 8);
        //todo: add associated parts sample data
        Main.inventory.addProduct(prod1);
        Main.inventory.addProduct(prod2);
        
    }
    
}
