package main.java.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.Main;
import main.java.Util;
import main.java.model.Part;
import main.java.model.Product;

public class ProductController {

    private Product currentProduct;
    private ObservableList<Part> unmodifiedPartsList;
    private ObservableList<Part> partSearchResults;
    private boolean isModifyProductView = false;
    
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
    private Label titleLabel;

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
        if(isModifyProductView) {
            // revert changes to associated parts
            currentProduct.getAssociatedParts().clear();
            for(Part p : unmodifiedPartsList) {
                currentProduct.addAssociatedPart(p);
            }
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
        if(isModifyProductView) {
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
        } else {
            currentProduct.setName(productNameTextField.getText());
            currentProduct.setInStock(Integer.parseInt(inventoryTextField.getText()));
            currentProduct.setMin(Integer.parseInt(minTextField.getText()));
            currentProduct.setMax(Integer.parseInt(maxTextField.getText()));
            currentProduct.setPrice(Double.parseDouble(priceTextField.getText()));
        }
        
        Util.getStageFromActionEvent(event).close();
    }

    @FXML
    void handleSearchButtonAction(ActionEvent event) {
        performPartsSearch();
    }
    
    @FXML
    void handleSearchFieldEnterKeyPressed(ActionEvent event) {
        performPartsSearch();
    }
    
    private void performPartsSearch() {
        String searchString = partSearchTextField.getText().trim().toLowerCase();
        
        //show all results if search field is blank
        if(searchString.equals("")) {
            availablePartsTable.setItems(Main.inventory.getAllParts());
        } else {
            partSearchResults = FXCollections.observableArrayList();
            Main.inventory.getAllParts().forEach(p -> {
                if (p.getName().toLowerCase().contains(searchString))  
                    partSearchResults.add(p);
            });            
            availablePartsTable.setItems(partSearchResults);
            if(partSearchResults.isEmpty()) {
                availablePartsTable.setPlaceholder(new Label("Search did not return any results."));
            }
        }
        
        //give focus back to search field and populate with the trimmed lowercase string
        partSearchTextField.setText(searchString);
        partSearchTextField.requestFocus();
    }
    
    
    public void initialize() {        
        System.out.println("initialized called");
        if(!isModifyProductView) {
            currentProduct = new Product();
            setupPartsTables();            
        }
        Util.setFocusListenerForCurrencyFormat(priceTextField);        
    }
    
    /**
     *initData is called from MainController to pass a product object to this controller.
     * @param product The product object to be modified.
     */
    protected void initData(Product product) {
        System.out.println("initData called");
        isModifyProductView = true;
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
        
        setupPartsTables();
    }
    
    /**
     * This method sets up the column to property bindings for the parts tables.  
     * It needs to be called from initialize() or initData() depending on if a 
     * product is being added or modified.
     */
    private void setupPartsTables() {
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
        selectedPartsTable.setItems(currentProduct.getAssociatedParts());
    }

}
