package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import main.java.Main;
import main.java.Util;
import main.java.model.InhousePart;
import main.java.model.OutsourcedPart;
import main.java.model.Part;

public class ModifyPartController {

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private ToggleGroup partTypeToggleGroup;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField partIdTextField;

    @FXML
    private TextField partNameTextField;

    @FXML
    private TextField inventoryTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField companyTextField;

    @FXML
    private TextField machineIdTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private HBox companyHBox;
    
    @FXML
    private HBox machineHBox;

    private InhousePart inhousePart;
    private OutsourcedPart outsourcedPart;
    
    @FXML
    protected void handleSaveButtonAction(ActionEvent event) {
        Util.getStageFromActionEvent(event).close();
        //todo: finish handleSaveButtonAction
    }
    
    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        Util.getStageFromActionEvent(event).close();
    }
    
    @FXML
    protected void handleInhouseRadioButtonAction(ActionEvent event) {
        companyHBox.setVisible(false);
        machineHBox.setVisible(true);
    }
    
    @FXML
    protected void handleOutsourcedRadioButtonAction(ActionEvent event) {
        machineHBox.setVisible(false);
        companyHBox.setVisible(true);
    }
    
    
}
