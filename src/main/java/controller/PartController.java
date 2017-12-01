package main.java.controller;

import java.text.NumberFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import main.java.Main;
import main.java.PartValidator;
import main.java.Util;
import main.java.Validator;
import main.java.model.InhousePart;
import main.java.model.OutsourcedPart;
import main.java.model.Part;

public class PartController {

    private boolean isModifyPartView = false;
    private Part currentPart;
    
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
    
    @FXML
    private Label titleLabel;
    
    @FXML
    protected void handleSaveButtonAction(ActionEvent event) { 
        //todo:  changed validation messages to be part/product specific - add machine id.
        Validator validator = (PartValidator)Validator.getValidator(
                Validator.ValidatorTypes.PART, 
                partNameTextField.getText(), 
                minTextField.getText(), 
                maxTextField.getText(), 
                inventoryTextField.getText(), 
                priceTextField.getText(),
                inHouseRadioButton.isSelected() ? machineIdTextField.getText() : null,
                null);
        if(validator.validate()) {
            if(isModifyPartView) {
                saveModifiedPart();
            } else {
                saveNewPart();
            }
        
            Util.getStageFromActionEvent(event).close();
        } else {
            Util.showErrorMessage(validator.getMessageAsString());
        }
    }
    
    private void saveModifiedPart() {
        if(currentPart instanceof InhousePart) {
            InhousePart currentInhousePart = (InhousePart)currentPart;
            currentInhousePart.setMachineID(Integer.parseInt(machineIdTextField.getText()));
            currentInhousePart.setName(partNameTextField.getText());
            currentInhousePart.setInStock(Integer.parseInt(inventoryTextField.getText()));
            currentInhousePart.setMin(Integer.parseInt(minTextField.getText()));
            currentInhousePart.setMax(Integer.parseInt(maxTextField.getText()));
            currentInhousePart.setPrice(Util.getDoubleFromCurrencyInstance(priceTextField.getText()));
        } else {
            OutsourcedPart currentOutsourcedPart = (OutsourcedPart)currentPart;
            currentOutsourcedPart.setCompanyName(companyTextField.getText());
            currentOutsourcedPart.setName(partNameTextField.getText());
            currentOutsourcedPart.setInStock(Integer.parseInt(inventoryTextField.getText()));
            currentOutsourcedPart.setMin(Integer.parseInt(minTextField.getText()));
            currentOutsourcedPart.setMax(Integer.parseInt(maxTextField.getText()));
            currentOutsourcedPart.setPrice(Util.getDoubleFromCurrencyInstance(priceTextField.getText()));
        }
    }
    
    private void saveNewPart() {
        if(inHouseRadioButton.isSelected()) {
            InhousePart currentPart = new InhousePart(                
                    Integer.parseInt(machineIdTextField.getText()),
                    partNameTextField.getText(),
                    Util.getDoubleFromCurrencyInstance(priceTextField.getText()),
                    Integer.parseInt(inventoryTextField.getText()),
                    Integer.parseInt(minTextField.getText()),
                    Integer.parseInt(maxTextField.getText())                            
            );
            Main.inventory.addPart(currentPart);
        } else {
            OutsourcedPart currentPart = new OutsourcedPart(
                    companyTextField.getText(),
                    partNameTextField.getText(),
                    Util.getDoubleFromCurrencyInstance(priceTextField.getText()),
                    Integer.parseInt(inventoryTextField.getText()),
                    Integer.parseInt(minTextField.getText()),
                    Integer.parseInt(maxTextField.getText())
            );
            Main.inventory.addPart(currentPart);
        }
    }
    
    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        if(Util.askForUserConfirmation("Are you sure you'd like to cancel?")) {
            Util.getStageFromActionEvent(event).close();
        }
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
    
    public void initialize() {
        if(!isModifyPartView) {
            partIdTextField.setText(Integer.toString(Part.getNextPartID()));
        }
        Util.setFocusListenerForCurrencyFormat(priceTextField);
    }
    
    protected void initData(Part part) {
        isModifyPartView = true;
        currentPart = part;
        
        //set initial values in UI fields
        partIdTextField.setText(Integer.toString(part.getPartID()));
        partNameTextField.setText(part.getName());
        inventoryTextField.setText(Integer.toString(part.getInStock()));
        priceTextField.setText(NumberFormat.getCurrencyInstance().format(part.getPrice()));
        minTextField.setText(Integer.toString(part.getMin()));
        maxTextField.setText(Integer.toString(part.getMax()));
        
        //setup UI fields and radio buttons dependent on part type
        if(part instanceof InhousePart) {
            machineIdTextField.setText(Integer.toString(((InhousePart) part).getMachineID()));
            inHouseRadioButton.setSelected(true);
            companyHBox.setVisible(false);
            machineHBox.setVisible(true);
        } else {
            companyTextField.setText(((OutsourcedPart)part).getCompanyName());
            outsourcedRadioButton.setSelected(true);
            machineHBox.setVisible(false);
            companyHBox.setVisible(true);
        }
        
        //don't allow the part type to be changed for an existing part
        inHouseRadioButton.setDisable(true);
        outsourcedRadioButton.setDisable(true);
    }
    
}
