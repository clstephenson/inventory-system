package main.java.controller;

import java.text.NumberFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.Main;
import main.java.ProductValidator;
import main.java.Util;
import main.java.Validator;
import main.java.model.Part;
import main.java.model.Product;

public class ProductController {

    private Product currentProduct; // current product being modified
    private ObservableList<Part> unmodifiedPartsList; // original associated parts list to revert to if changes are cancelled
    private boolean isModifyProductView = false; // true indicates new product
    
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
    private TableView<Part> selectedPartsTable;

    @FXML
    private TableColumn<Part, Integer> usedPartsPartIDTableColumn;

    @FXML
    private TableColumn<Part, String> usedPartsNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> usedPartsInventoryTableColumn;

    @FXML
    private TableColumn<Part, Double> usedPartsPriceTableColumn;

    /**
     * Event handler for when the add part button is clicked.
     * @param event
     */
    @FXML
    void handleAddPartButtonAction(ActionEvent event) {  
        if(availablePartsTable.getSelectionModel().getSelectedItem() == null) {
            Util.showErrorMessage("Please select a part to add.");
        } else {
            currentProduct.addAssociatedPart(availablePartsTable.getSelectionModel().getSelectedItem());  
        }
    }

    /**
     * Event handler for when the cancel button is clicked.
     * @param event
     */
    @FXML
    void handleCancelButtonAction(ActionEvent event) {
        if(Util.askForUserConfirmation("Are you sure you'd like to cancel?")) {
            if(isModifyProductView) {
                // revert changes to associated parts
                currentProduct.getAssociatedParts().clear();
                unmodifiedPartsList.forEach(p -> currentProduct.addAssociatedPart(p));
            }
            // close the current window
            Util.getStageFromActionEvent(event).close();
        }
    }

    /**
     * Event handler for when the delete part button is clicked.
     * @param event
     */
    @FXML
    void handleDeletePartButtonAction(ActionEvent event) {
        if(Util.askForUserConfirmation("Are you sure you'd like to remove the selected part?")) {
            if(selectedPartsTable.getSelectionModel().getSelectedItem() == null) {
                Util.showErrorMessage("Please select a part to remove.");
            } else {
                currentProduct.removeAssociatedPart(selectedPartsTable.getSelectionModel().getSelectedItem().getPartID());
            }
        }
    }

    /**
     * Event handler for when the save button is clicked.
     * @param event
     */
    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        // get a ProductValidator object to validate field values before saving.
        Validator validator = (ProductValidator)Validator.getValidator(
                Validator.ValidatorTypes.PRODUCT, 
                productNameTextField.getText(), 
                minTextField.getText(), 
                maxTextField.getText(), 
                inventoryTextField.getText(), 
                priceTextField.getText(), 
                null,
                Util.getArrayListCopyOfObservableList(currentProduct.getAssociatedParts()));
        if(validator.validate()) { 
            // validation passes, save the product
            currentProduct.setName(productNameTextField.getText());
            currentProduct.setInStock(Integer.parseInt(inventoryTextField.getText()));
            currentProduct.setMin(Integer.parseInt(minTextField.getText()));
            currentProduct.setMax(Integer.parseInt(maxTextField.getText()));
            currentProduct.setPrice(Util.getDoubleFromCurrencyInstance(priceTextField.getText()));
                
            // if a new product, then add to the inventory
            if(!isModifyProductView) {
                Main.inventory.addProduct(currentProduct);
            }
            // close the current stage after saving the part
            Util.getStageFromActionEvent(event).close();
        } else {
            // show the validation errors to the user
            Util.showErrorMessage(validator.getMessageAsString());
        }                
    }

    /**
     * Event handler for when the search button is clicked.
     * @param event
     */
    @FXML
    void handleSearchButtonAction(ActionEvent event) {
        performPartsSearch();
    }
    
    /**
     * Event handler for when the enter key is pressed and the search text field has focus.
     * @param event
     */
    @FXML
    void handleSearchFieldEnterKeyPressed(ActionEvent event) {
        performPartsSearch();
    }
    
    /**
     * find the parts in inventory that match the text in the search text field
     */
    private void performPartsSearch() {
        String searchString = partSearchTextField.getText().trim().toLowerCase();
                
        if(searchString.equals("")) {
            //show all results if search field is blank
            reloadAvailablePartsTableData();
        } else {
            ObservableList<Part> searchResults = FXCollections.observableArrayList();
            // iterate through all parts and if match is found, add it to searchresults.
            Main.inventory.getAllParts().forEach(p -> {
                if (p.getName().toLowerCase().contains(searchString))  
                    searchResults.add(p);
            });            
            // if no results found, then show a message and reload the data
            // otherwise, load the table with the search results
            if(searchResults.isEmpty()) {
                Util.showErrorMessage("Search did not return any results.");
                reloadAvailablePartsTableData();
            } else {
                availablePartsTable.setItems(searchResults);
            }
        }
        
        //give focus back to search field and populate with the trimmed lowercase string
        partSearchTextField.setText(searchString);
        partSearchTextField.requestFocus();
        partSearchTextField.selectAll();
    }
    
    /**
     * reset the available parts table to match inventory contents
     */
    private void reloadAvailablePartsTableData() {
        availablePartsTable.setItems(Main.inventory.getAllParts());
    }
    
    /**
     * reset the selected parts table to match the current product associated parts
     */
    private void reloadSelectedPartsTableData() {
        selectedPartsTable.setItems(currentProduct.getAssociatedParts());
    }
    
    /**
     * reset the contents of both parts tables
     */
    private void reloadBothPartsTables() {
        reloadAvailablePartsTableData();
        reloadSelectedPartsTableData();
    }
        
    /**
     * Initialization when the view is shown
     */
    public void initialize() {  
        // if new product is requested, create a new Product object and set up the parts tables
        if(!isModifyProductView) {
            currentProduct = new Product();
            setupPartsTables();            
        }
        // set up focus listeners on text fields to set formatting or default values
        Util.setFocusListenerForCurrencyFormat(priceTextField);  
        Util.setFocusListenerForEmptyNumericFields(minTextField, maxTextField, inventoryTextField);
    }
    
    /**
     * initData is called from MainController to pass a product object to this controller.
     * @param product The product object to be modified.
     */
    protected void initData(Product product) {
        isModifyProductView = true;
        currentProduct = product;
        unmodifiedPartsList = FXCollections.observableArrayList();
        product.getAssociatedParts().forEach(p -> unmodifiedPartsList.add(p));
        
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
        
        reloadBothPartsTables();
    }

}
