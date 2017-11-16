package main.java.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.Main;
import main.java.Util;
import main.java.model.Part;
import main.java.model.Product;

public class MainController implements Initializable{
    
    @FXML
    private Button exitButton;

    @FXML
    private TextField partSearchTextField;

    @FXML
    private Button partSearchButton;

    @FXML
    private TableView<Part> partsTable;
    
     @FXML
    private TableColumn<Part, Integer> partIDTableColumn;

    @FXML
    private TableColumn<Part, String> partNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryTableColumn;

    @FXML
    private TableColumn<Part, Double> partPriceTableColumn;

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
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIDTableColumn;

    @FXML
    private TableColumn<Product, String> productNameTableColumn;

    @FXML
    private TableColumn<Product, Integer> productInventoryTableColumn;

    @FXML
    private TableColumn<Product, Double> productPriceTableColumn;
    
    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    protected void handleExitButtonAction(ActionEvent event) {
        Util.getStageFromActionEvent(event).close();
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
        //todo: implement addPartButtonAction
        Stage window = Util.showModalWindow("AddPart.fxml", Util.getStageFromActionEvent(event), "Add Part");
    }
    
    @FXML
    protected void modifyPartButtonAction(ActionEvent event) {
        //todo: implement modifyPartButtonAction
        Stage window = Util.showModalWindow("ModifyPart.fxml", Util.getStageFromActionEvent(event), "Modify Part");
    }
    
    @FXML
    protected void deletePartButtonAction(ActionEvent event) {
        //todo: check for deletePart returning false
        int selectedPartID = partsTable.getSelectionModel().getSelectedItem().getPartID();
        if (!Main.inventory.deletePart(selectedPartID)) {
            Util.showErrorMessage("The selected part could not be deleted.");
        }        
    }
    
    @FXML
    protected void addProductButtonAction(ActionEvent event) {
        Stage window = Util.showModalWindow("AddProduct.fxml", Util.getStageFromActionEvent(event), "Add Product");
        //todo: implement addProductButtonAction
    }
    
    @FXML
    protected void modifyProductButtonAction(ActionEvent event) {
        Stage window = Util.showModalWindow("ModifyProduct.fxml", Util.getStageFromActionEvent(event), "Modify Product");
        //todo: implement modifyProductButtonAction
    }
    
    @FXML
    protected void deleteProductButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement deleteProductButtonAction
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("inStock"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        
        partsTable.setItems(Main.inventory.getAllParts());
        productsTable.setItems(Main.inventory.getAllProducts());
    }
    
}
