package main.java;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.Inventory;

public class Main extends Application {

    public static Inventory inventory;
    
    public static void main(String[] args) {
        inventory = new Inventory();
        Util.createSampleData();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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
