package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.java.Util;

public class AddProductController {

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
    private TableView<?> availablePartsTable;

    @FXML
    private TableColumn<?, ?> availPartsPartIDTableColumn;

    @FXML
    private TableColumn<?, ?> availPartsNameTableColumn;

    @FXML
    private TableColumn<?, ?> availPartsInventoryTableColumn;

    @FXML
    private TableColumn<?, ?> availPartsPriceTableColumn;

    @FXML
    private Button addPartButton;

    @FXML
    private TableView<?> selectedPartsTable;

    @FXML
    private TableColumn<?, ?> usedPartsPartIDTableColumn;

    @FXML
    private TableColumn<?, ?> usedPartsNameTableColumn;

    @FXML
    private TableColumn<?, ?> usedPartsInventoryTableColumn;

    @FXML
    private TableColumn<?, ?> usedPartsPriceTableColumn;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void handleAddPartButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement handleAddPartButtonAction
    }

    @FXML
    void handleCancelButtonAction(ActionEvent event) {
        Util.getStageFromActionEvent(event).close();
    }

    @FXML
    void handleDeletePartButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement handleDeletePartButtonAction
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        Util.getStageFromActionEvent(event).close();
        throw new RuntimeException("not implemented");
        //todo: implement handleSaveButtonAction
    }

    @FXML
    void handleSearchButtonAction(ActionEvent event) {
        throw new RuntimeException("not implemented");
        //todo: implement handleSearchButtonAction
    }

}
