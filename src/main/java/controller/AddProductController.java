package main.java.controller;

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

public class AddProductController {
    
    private Product currentProduct;
    private ObservableList<Part> partSearchResults;

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
        //todo: implement handleAddPartButtonAction
    }

    @FXML
    void handleCancelButtonAction(ActionEvent event) {
        Util.getStageFromActionEvent(event).close();
    }

    @FXML
    void handleDeletePartButtonAction(ActionEvent event) {
        if(selectedPartsTable.getSelectionModel().getSelectedItem() == null) {
            Util.showErrorMessage("Please select a part to remove.");
        } else {
            currentProduct.removeAssociatedPart(selectedPartsTable.getSelectionModel().getSelectedItem().getPartID());
        }
        //todo: implement handleDeletePartButtonAction
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        currentProduct.setName(productNameTextField.getText());
        currentProduct.setInStock(Integer.parseInt(inventoryTextField.getText()));
        currentProduct.setMin(Integer.parseInt(minTextField.getText()));
        currentProduct.setMax(Integer.parseInt(maxTextField.getText()));
        currentProduct.setPrice(Double.parseDouble(priceTextField.getText()));
        Util.getStageFromActionEvent(event).close();
        //todo: implement handleSaveButtonAction
    }

    @FXML
    void handleSearchButtonAction(ActionEvent event) {
        String searchString = partSearchTextField.getText().toLowerCase();
        //only search if at least 3 characters are entered
        if(searchString.length() >= 3) {
            partSearchResults = FXCollections.observableArrayList();
            Main.inventory.getAllParts().forEach(p -> {
                if (p.getName().toLowerCase().contains(searchString))  
                    partSearchResults.add(p);
            });
            availablePartsTable.setItems(partSearchResults);
        }
        //todo: handle no search results condition
    }
    
    public void initialize() {
        currentProduct = new Product();
        Util.setFocusListenerForCurrencyFormat(priceTextField);
        
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
