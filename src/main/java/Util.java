/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.model.InhousePart;
import main.java.model.OutsourcedPart;
import main.java.model.Part;
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
    
    public static void setFocusListenerForCurrencyFormat(TextField node) {
        //todo:  need to handle blank or non-numeric values
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // field has received focus
            if(newValue == true) {
                NumberFormat cf = NumberFormat.getCurrencyInstance();                
                if(node.getText().matches("^\\$[0-9,]*\\.[0-9]*$")) {
                    try {
                        node.setText(Double.toString(cf.parse(node.getText()).doubleValue()));
                    } catch (ParseException ex) {
                        Util.showErrorMessage(ex.getMessage(), ex);
                    }
                }                
            // field has lost focus
            } else {
                if(node.getText().matches("^[0-9]+\\.?[0-9]*$")) {
                    node.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(node.getText())));
                } else {
                    Util.showErrorMessage("Please enter a numeric value for the price.");
                    node.requestFocus();                    
                }
            }
        });
    }
    
    public static double getDoubleFromCurrencyInstance(String currencyFormattedString) {
        NumberFormat cf = NumberFormat.getCurrencyInstance();
            Number price = null;
            try {
                price = cf.parse(currencyFormattedString);
            } catch (ParseException ex) {
                Util.showErrorMessage(ex.getMessage(), ex);
            }
            return price.doubleValue();
    }
    
    /**
     *Build some sample part and product data and add to inventory
     */
    public static void createSampleData() {
        //load sample part data into inventory
        Main.inventory.addPart(new InhousePart(87, "Widget #1", 134.50, 5, 3, 10));
        Main.inventory.addPart(new InhousePart(67, "Part #2", 57.25, 23, 15, 25));
        Main.inventory.addPart(new OutsourcedPart("Widget Company", "Widget #3", 98.00, 8, 5, 10));
        Main.inventory.addPart(new InhousePart(54, "Part #4", 4.00, 45, 30, 50));
        Main.inventory.addPart(new OutsourcedPart("Acme", "Widget #5", 1238.89, 12, 10, 15));
        Main.inventory.addPart(new InhousePart(2, "Part #6", 60.00, 10, 5, 10));
        
        //load sample product data into inventory
        Main.inventory.addProduct(new Product("Motor Assembly", 2500.00, 5, 2, 8));
        Main.inventory.addProduct(new Product("Turbine", 10350.00, 6, 2, 8));
        Main.inventory.addProduct(new Product("Fan Assembly", 500.00, 7, 2, 8));
        Main.inventory.addProduct(new Product("Gearbox", 950.00, 8, 2, 8));
        
        //load a random associated part into each product
        for(Product prod : Main.inventory.getAllProducts()) {
            Random rand = new Random();
            int randomID = rand.nextInt(Main.inventory.getAllParts().size() - 1);
            Part part = Main.inventory.getAllParts().get(randomID);
            prod.addAssociatedPart(part);
        }
        
    }
    
}
