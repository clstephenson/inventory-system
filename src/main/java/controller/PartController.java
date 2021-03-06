package main.java.controller;

import java.text.NumberFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.java.Main;
import main.java.PartValidator;
import main.java.Util;
import main.java.Validator;
import main.java.model.InhousePart;
import main.java.model.OutsourcedPart;
import main.java.model.Part;

public class PartController {

    private boolean isModifyPartView = false; // true indicates a new part
    private Part currentPart; // object currently being modified
    
    @FXML
    private RadioButton inHouseRadioButton;

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
    private HBox companyHBox;
    
    @FXML
    private HBox machineHBox;
    
    /**
     * Event handler for when the save button is clicked.
     * @param event
     */
    @FXML
    protected void handleSaveButtonAction(ActionEvent event) { 
        // get a PartValidator object to validate field values before saving.
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
            // validation passes, save the part
            if(isModifyPartView) {
                saveModifiedPart();
            } else {
                saveNewPart();
            }    
            // close the current stage after saving the part
            Util.getStageFromActionEvent(event).close();
        } else {
            // show the validation errors to the user
            Util.showErrorMessage(validator.getMessageAsString());
        }
    }
    
    /**
     * Update the current part object with the new data from the UI.
     */
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
    
    /**
     * Create a new InhousePart or OutsourcedPart from the data entered in the UI.
     */
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
    
    /**
     * Event handler for when the cancel button is clicked.
     * @param event
     */
    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        if(Util.askForUserConfirmation("Are you sure you'd like to cancel?")) {
            Util.getStageFromActionEvent(event).close();
        }
    }
    
    /**
     * Event handler for when the radio button for inhouse part is clicked.
     * @param event
     */
    @FXML
    protected void handleInhouseRadioButtonAction(ActionEvent event) {
        companyHBox.setVisible(false);
        machineHBox.setVisible(true);
    }
    
    /**
     * Event handler for when the radio button for outsourced part is clicked.
     * @param event
     */
    @FXML
    protected void handleOutsourcedRadioButtonAction(ActionEvent event) {
        machineHBox.setVisible(false);
        companyHBox.setVisible(true);
    }
    
    /**
     * Initialization when the view is shown
     */
    public void initialize() {
        // if new part is requested, then pre-fill the part ID field with the next available ID.
        if(!isModifyPartView) {
            partIdTextField.setText(Integer.toString(Part.getNextPartID()));
        }
        
        // set up focus listeners on text fields to set formatting or default values
        Util.setFocusListenerForCurrencyFormat(priceTextField);
        Util.setFocusListenerForEmptyNumericFields(minTextField, maxTextField, inventoryTextField, machineIdTextField);
    }
    
    /**
     * initData is called from MainController to pass a Part object to this controller.
     * @param part The part object to be modified.
     */
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
