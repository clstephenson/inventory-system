package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.Util;

public class MainController {
    
    @FXML
    private Button exitButton;

    @FXML
    private TextField partSearchTextField;

    @FXML
    private Button partSearchButton;

    @FXML
    private TableView<?> partsTable;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TextField productSearchTextField;

    @FXML
    private Button productSearchButton;

    @FXML
    private TableView<?> productsTable;

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    protected void handleExitButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement handleExitButtonAction
    }
    
    @FXML
    protected void partSearchButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement partSearchButtonAction
    }
    
    @FXML
    protected void productSearchButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement productSearchButtonAction
    }
    
    @FXML
    protected void addPartButtonAction(ActionEvent event) {
        //throw new RuntimeException("not implemented");
        //todo: implement addPartButtonAction
        Stage window = Util.showModalWindow("1AddPart.fxml", (Button)event.getSource(), "Add Part");
    }
    
    @FXML
    protected void modifyPartButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement modifyPartButtonAction
    }
    
    @FXML
    protected void deletePartButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement deletePartButtonAction
    }
    
    @FXML
    protected void addProductButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement addProductButtonAction
    }
    
    @FXML
    protected void modifyProductButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement modifyProductButtonAction
    }
    
    @FXML
    protected void deleteProductButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement deleteProductButtonAction
    }
}
