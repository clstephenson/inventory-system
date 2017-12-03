package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.Inventory;

/**
 * Main application
 * @author Chris Stephenson
 */
public class Main extends Application {

    public static Inventory inventory;
    
    /**
     * Main entry point for the application.
     * @param args
     */
    public static void main(String[] args) {
        inventory = new Inventory();
        Util.createSampleData();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // set up and load the primary stage (window).
        Stage window = primaryStage;        
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Util.FXML_PATH + "Main.fxml"));
        } catch (Exception e) {
            Util.showErrorMessage("Could not load Main.fxml.", e);
        }        
        Scene scene = new Scene(root, 800, 600);
        window.setTitle("Inventory Management System");
        window.setScene(scene);
        window.show();
    }
}
