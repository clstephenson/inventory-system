package main.java;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
 * Utility class to hold static helper methods and variables.
 * 
 * @author Chris Stephenson
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
     * Display a confirmation dialog to the user
     * @param message Message to be displayed
     * @return true if OK button was pressed, otherwise false
     */
    public static boolean askForUserConfirmation(String message) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Please Confirm...");
        confirmation.setContentText(message);
        Optional<ButtonType> result = confirmation.showAndWait();
        return result.get() == ButtonType.OK;
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
    
    /**
     * Set up automatic currency formatting on all values in a specified table column. 
     * @param <T> Type of the class contained in the TableView.items list
     * @param col TableColumn object to format
     */
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
    
    /**
     * Set up a listener on a text field to automatically add currency formatting when the field 
     * loses focus, and removes currency formatting when the field has focus.
     * @param node TextField to watch for focus
     */
    public static void setFocusListenerForCurrencyFormat(TextField node) {
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // field has received focus
            if(newValue == true) {
                NumberFormat cf = NumberFormat.getCurrencyInstance(); 
                // check if the text matches a currency format
                if(node.getText().matches("^\\$[0-9,]*\\.[0-9]*$")) {
                    try {
                        // remove the currency formatting
                        node.setText(Double.toString(cf.parse(node.getText()).doubleValue()));
                    } catch (ParseException ex) {
                        Util.showErrorMessage(ex.getMessage(), ex);
                    }
                }                
            // field has lost focus
            } else {
                // check for string representation of a double
                if(node.getText().matches("^[0-9]+\\.?[0-9]*$")) {
                    // add currency formatting to the string
                    node.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(node.getText())));
                } else {
                    // if the price string is blank, set a default value
                    if(node.getText().isEmpty()) {
                        node.setText("$0.00");
                    } else {
                        Util.showErrorMessage("Please enter a numeric value for the price.");
                        node.requestFocus();
                    }
                }
            }
        });
    }
    
    /**
     * Set up a listener on a TextField nodes to check if the value is empty when the field 
     * loses focus, and if so, set a default value of 0.
     * @param nodes TextField nodes that require the listener
     */
    public static void setFocusListenerForEmptyNumericFields(TextField ... nodes) {
        for (TextField node : nodes) {
            node.focusedProperty().addListener((observable, oldValue, newValue) -> {
                // field has received focus
                if (newValue == false) {
                    // if the node text is blank, set a default value of 0
                    if (node.getText().isEmpty()) {
                        node.setText("0");
                    }                    
                }
            });
        }
    }
    
    /**
     * Get the numeric double value from a currency formatted string.  Example:  returns 5.0 for "$5.00".
     * @param currencyFormattedString string formatted as currency (ex. "$5.00")
     * @return double numeric value represented by the string
     */
    public static double getDoubleFromCurrencyInstance(String currencyFormattedString) {
        NumberFormat cf = NumberFormat.getCurrencyInstance();
            Number price = null;
            try {
                price = cf.parse(currencyFormattedString);
            } catch (ParseException ex) {
                Util.showErrorMessage(ex.getMessage(), ex);
            }            
            return price.doubleValue(); 
            //todo: fix possible null reference warning
    }
    
    /**
     * Copy items in an ObservableList object to a new ArrayList.  Note: object references
     * will be copied into the new list, not copies of the objects.
     * @param list The ObservableList to copy
     * @return ArrayList 
     */
    public static ArrayList getArrayListCopyOfObservableList(ObservableList list) {
        ArrayList copy = new ArrayList();
        list.forEach(item -> copy.add(item));
        return copy;
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
        Main.inventory.getAllProducts().forEach((prod) -> {
            Random rand = new Random();
            int randomID = rand.nextInt(Main.inventory.getAllParts().size() - 1);
            Part part = Main.inventory.getAllParts().get(randomID);
            prod.addAssociatedPart(part);
        });
        
    }
    
}
