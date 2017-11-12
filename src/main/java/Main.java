package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Main.fxml"));
        
        Scene scene = new Scene(root, 800, 600);
        window.setTitle("Inventory Management System");
        window.setScene(scene);
        window.show();
    }
}
