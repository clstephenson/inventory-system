package main.java.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.Main;
import main.java.Util;
import main.java.model.Part;
import main.java.model.Product;

public class ModifyProductController {

    private Product currentProduct;
    private ObservableList<Part> unmodifiedPartsList;
    
    @FXML
    private TextField productIdTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField inventoryTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField partSearchTextField;

    @FXML
    private Button partSearchButton;

    @FXML
    private TableView<Part> availablePartsTable;

    @FXML
    private TableColumn<Part, Integer> availPartsPartIDTableColumn;

    @FXML
    private TableColumn<Part, String> availPartsNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> availPartsInventoryTableColumn;

    @FXML
    private TableColumn<Part, Double> availPartsPriceTableColumn;

    @FXML
    private Button addPartButton;

    @FXML
    private TableView<Part> selectedPartsTable;

    @FXML
    private TableColumn<Part, Integer> usedPartsPartIDTableColumn;

    @FXML
    private TableColumn<Part, String> usedPartsNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> usedPartsInventoryTableColumn;

    @FXML
    private TableColumn<Part, Double> usedPartsPriceTableColumn;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void handleAddPartButtonAction(ActionEvent event) {  
        if(availablePartsTable.getSelectionModel().getSelectedItem() == null) {
            Util.showErrorMessage("Please select a part to add.");
        } else {
            currentProduct.addAssociatedPart(availablePartsTable.getSelectionModel().getSelectedItem());  
        }
    }

    @FXML
    void handleCancelButtonAction(ActionEvent event) {
        currentProduct.getAssociatedParts().clear();
        for(Part p : unmodifiedPartsList) {
            currentProduct.addAssociatedPart(p);
        }
        Util.getStageFromActionEvent(event).close();
    }

    @FXML
    void handleDeletePartButtonAction(ActionEvent event) {
        if(selectedPartsTable.getSelectionModel().getSelectedItem() == null) {
            Util.showErrorMessage("Please select a part to remove.");
        } else {
            currentProduct.removeAssociatedPart(selectedPartsTable.getSelectionModel().getSelectedItem().getPartID());
        }
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        currentProduct.setName(productNameTextField.getText());
        NumberFormat cf = NumberFormat.getCurrencyInstance();
        Number price = null;
        try {
            price = cf.parse(priceTextField.getText());
        } catch (ParseException ex) {
            Util.showErrorMessage(ex.getMessage(), ex);
        }
        currentProduct.setPrice(price.doubleValue());
        currentProduct.setInStock(Integer.parseInt(inventoryTextField.getText()));
        currentProduct.setMin(Integer.parseInt(minTextField.getText()));
        currentProduct.setMax(Integer.parseInt(maxTextField.getText()));
        
        Util.getStageFromActionEvent(event).close();
        
        //todo: product table on main view not being updated with changes
    }

    @FXML
    void handleSearchButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement handleSearchButtonAction
    }
    
    public void initialize() {
        Util.setFocusListenerForCurrencyFormat(priceTextField);
    }
    
    protected void initData(Product product) {
        currentProduct = product;
        unmodifiedPartsList = FXCollections.observableArrayList();
        for(Part p : product.getAssociatedParts()) {
            unmodifiedPartsList.add(p);
        }
        
        productIdTextField.setText(Integer.toString(product.getProductID()));
        productNameTextField.setText(product.getName());
        inventoryTextField.setText(Integer.toString(product.getInStock()));
        priceTextField.setText(NumberFormat.getCurrencyInstance().format(product.getPrice()));
        minTextField.setText(Integer.toString(product.getMin()));
        maxTextField.setText(Integer.toString(product.getMax()));
        
        availPartsPartIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        availPartsNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartsInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        availPartsPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        usedPartsPartIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        usedPartsNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usedPartsInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        usedPartsPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        Util.setCurrencyFormattingOnTableColumn(availPartsPriceTableColumn);
        Util.setCurrencyFormattingOnTableColumn(usedPartsPriceTableColumn);
        
        availablePartsTable.setItems(Main.inventory.getAllParts());
        selectedPartsTable.setItems(product.getAssociatedParts());
    }

}
