package main.java.controller;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.Main;
import main.java.Util;
import main.java.model.Part;
import main.java.model.Product;

public class MainController {
    
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
        openPartWindow(event, false);
    }
    
    @FXML
    protected void modifyPartButtonAction(ActionEvent event) {
        openPartWindow(event, true);
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
        openProductWindow(event, false);
    }
    
    @FXML
    protected void modifyProductButtonAction(ActionEvent event) {
        openProductWindow(event, true);
    }
    
    protected void openPartWindow(ActionEvent event, boolean modifyPart) {
        String title = modifyPart ? "Modify Part" : "Add Part";
        Stage window = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        URL location;
        try {
            location = Util.class.getResource(Util.FXML_PATH + "Part.fxml");
            loader.setLocation(location);
            root = (Parent)loader.load(location.openStream());
            if(modifyPart) {
                PartController controller = (PartController)loader.getController();
                Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
                controller.initData(selectedPart);
            }
        } catch (NullPointerException ex) {
            Util.showErrorMessage("Please select a part from the list first.");
            return;
        } catch (Exception ex) {
            Util.showErrorMessage(ex.getMessage(), ex);
        }  
        Label label = (Label)loader.getNamespace().get("titleLabel");
        label.setText(title);
        window.setScene(new Scene(root)); 
        window.setTitle(title);       
        window.setResizable(false);
        window.initOwner(Util.getStageFromActionEvent(event));
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
        partsTable.refresh();
    }
    
    protected void openProductWindow(ActionEvent event, boolean modifyProduct) {
        String title = modifyProduct ? "Modify Product" : "Add Product";
        Stage window = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        URL location;
        try {
            location = Util.class.getResource(Util.FXML_PATH + "Product.fxml");
            loader.setLocation(location);
            root = (Parent)loader.load(location.openStream());
            if(modifyProduct) {
                ProductController controller = (ProductController)loader.getController();
                Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
                controller.initData(selectedProduct);
            }
        } catch (NullPointerException ex) {
            Util.showErrorMessage("Please select a product from the list first.");
            return;
        } catch (Exception ex) {
            Util.showErrorMessage(ex.getMessage(), ex);
        }  
        Label label = (Label)loader.getNamespace().get("titleLabel");
        label.setText(title);
        window.setScene(new Scene(root)); 
        window.setTitle(title);       
        window.setResizable(false);
        window.initOwner(Util.getStageFromActionEvent(event));
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
        productsTable.refresh();
    }
    
    @FXML
    protected void deleteProductButtonAction(ActionEvent event) {
        int selectedProductID = productsTable.getSelectionModel().getSelectedItem().getProductID();
        if (!Main.inventory.removeProduct(selectedProductID)) {
            Util.showErrorMessage("The selected product could not be deleted.");
        }  
    }
        
    public void initialize() {
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                        
        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
               
        Util.setCurrencyFormattingOnTableColumn(partPriceTableColumn);
        Util.setCurrencyFormattingOnTableColumn(productPriceTableColumn);
                
        partsTable.setItems(Main.inventory.getAllParts());
        productsTable.setItems(Main.inventory.getAllProducts());
    }
    
    
    
}
