package main.java.controller;

import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.Main;
import main.java.Util;
import main.java.model.Part;
import main.java.model.Product;

public class MainController {
    
    @FXML
    private TextField partSearchTextField;

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
    private TextField productSearchTextField;

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
    
    /**
     * Event handler for when the enter key is pressed and the search text field has focus.
     * @param event
     */
    @FXML
    void handleSearchFieldEnterKeyPressed(ActionEvent event) {
        TextField sourceField = (TextField)event.getSource();
        if(sourceField == productSearchTextField){
            performProductSearch();
        } else if(sourceField == partSearchTextField) {
            performPartSearch();
        }
    }
    
    /**
     * Event handler for when the exit button is clicked.
     * @param event
     */
    @FXML
    protected void handleExitButtonAction(ActionEvent event) {
        Util.getStageFromActionEvent(event).close();
    }
    
    /**
     * Event handler for when the part search button is clicked.
     * @param event
     */
    @FXML
    protected void partSearchButtonAction(ActionEvent event) {
        performPartSearch();
    }
    
    
    private void performPartSearch() {
        String searchString = partSearchTextField.getText().trim().toLowerCase();
        
        //show all results if search field is blank
        if(searchString.equals("")) {
            reloadPartsTableData();
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
                reloadPartsTableData();
            } else {
                partsTable.setItems(searchResults);
            }
        }        
        //give focus back to search field and populate with the trimmed lowercase string
        partSearchTextField.setText(searchString);
        partSearchTextField.requestFocus();
        partSearchTextField.selectAll();
    }
    
    /**
     * Event handler for when the product search button is clicked.
     * @param event
     */
    @FXML
    protected void productSearchButtonAction(ActionEvent event) {
        performProductSearch();
    }
    
    private void performProductSearch() {
        String searchString = productSearchTextField.getText().trim().toLowerCase();
        
        //show all results if search field is blank
        if(searchString.equals("")) {
            reloadProductsTableData();
        } else {
            ObservableList<Product> searchResults = FXCollections.observableArrayList();
            // iterate through all products and if match is found, add it to searchresults.
            Main.inventory.getAllProducts().forEach(p -> {
                if (p.getName().toLowerCase().contains(searchString))  
                    searchResults.add(p);
            });            
            // if no results found, then show a message and reload the data
            // otherwise, load the table with the search results
            if(searchResults.isEmpty()) {
                Util.showErrorMessage("Search did not return any results.");
                reloadProductsTableData();
            } else {
                productsTable.setItems(searchResults);
            }
        }        
        //give focus back to search field and populate with the trimmed lowercase string
        productSearchTextField.setText(searchString);
        productSearchTextField.requestFocus();
        productSearchTextField.selectAll();
    }
    
    /**
     * Event handler for when the add part button is clicked.
     * @param event
     */
    @FXML
    protected void addPartButtonAction(ActionEvent event) {
        openPartWindow(event, false);
    }
    
    /**
     * Event handler for when the modify part button is clicked.
     * @param event
     */
    @FXML
    protected void modifyPartButtonAction(ActionEvent event) {
        openPartWindow(event, true);
    }
     
    /**
     * Event handler for when the delete part button is clicked.
     * @param event
     */
    @FXML
    protected void deletePartButtonAction(ActionEvent event) {
        if(Util.askForUserConfirmation("Are you sure you'd like to delete the selected part?")) {
            int selectedPartID = partsTable.getSelectionModel().getSelectedItem().getPartID();
            if (!Main.inventory.deletePart(selectedPartID)) {
                Util.showErrorMessage("The selected part could not be deleted.");
            }   
        }
    }
    
    /**
     * Event handler for when the add product button is clicked.
     * @param event
     */
    @FXML
    protected void addProductButtonAction(ActionEvent event) {
        openProductWindow(event, false);
    }
    
    /**
     * Event handler for when the add product button is clicked.
     * @param event
     */
    @FXML
    protected void modifyProductButtonAction(ActionEvent event) {
        openProductWindow(event, true);
    }
    
    /**
     * Setup and show a new JavaFX stage using Part.fxml for the scene. 
     * @param event
     * @param modifyPart if true, then selected part data will be initialized in the controller object.
     */
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
                // get the controller and call the initData method to pass the selected part object prior to showing the window.
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
    
    /**
     * Setup and show a new JavaFX stage using Product.fxml for the scene. 
     * @param event
     * @param modifyProduct if true, then selected product data will be initialized in the controller object.
     */
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
                // get the controller and call the initData method to pass the selected product object prior to showing the window.
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
    
    /**
     * Event handler for when the delete product button is clicked.
     * @param event
     */
    @FXML
    protected void deleteProductButtonAction(ActionEvent event) {
        if(Util.askForUserConfirmation("Are you sure you'd like to delete the selected product?")) {
            Product productToDelete = productsTable.getSelectionModel().getSelectedItem();
            // is product has parts, do not allow deletion
            if(productToDelete.hasAssociatedParts()) {
                Util.showErrorMessage("Cannot delete product if it has associated parts.");
            } else { // try to delete part - show error if fails
                if(!Main.inventory.removeProduct(productToDelete.getProductID())) {
                    Util.showErrorMessage("The selected product could not be deleted.");
                }  
            }
        }
    }
        
    /**
     * set up the part and product table column bindings and add currency formatting to the price columns.
     */
    public void initialize() {
        //set up bindings for part table
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            
        // set up bindings for product table
        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
          
        // display the price columns with currency formatting
        Util.setCurrencyFormattingOnTableColumn(partPriceTableColumn);
        Util.setCurrencyFormattingOnTableColumn(productPriceTableColumn);
        
        reloadPartsAndProductsTablesFromInventory();
    }
    
    /**
     * reset both the products and parts tables to match inventory contents
     */
    private void reloadPartsAndProductsTablesFromInventory() {
        reloadPartsTableData();
        reloadProductsTableData();
    }
    
    /**
     * reset the contents of the products table to match inventory contents
     */
    private void reloadProductsTableData() {
        productsTable.setItems(Main.inventory.getAllProducts());
    }
    
     /**
     * reset the contents of the parts table to match inventory contents
     */
    private void reloadPartsTableData() {
        partsTable.setItems(Main.inventory.getAllParts());
    }
    
}
