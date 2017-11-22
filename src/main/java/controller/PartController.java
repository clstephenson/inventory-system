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
import main.java.Util;
import main.java.model.InhousePart;
import main.java.model.OutsourcedPart;
import main.java.model.Part;

public class PartController {

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

    private InhousePart inhousePart;
    private OutsourcedPart outsourcedPart;
    private boolean isModifyPartView = false;
    
    @FXML
    protected void handleSaveButtonAction(ActionEvent event) {        
        if(isModifyPartView) {
            //this is an existing part to be modified
            //todo: add save logic if existing part is modified
        } else {
            //this is a new part... create it and add to inventory
            if(inHouseRadioButton.isSelected()) {
                inhousePart = new InhousePart(
                        Integer.parseInt(machineIdTextField.getText()),
                        partNameTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Integer.parseInt(inventoryTextField.getText()),
                        Integer.parseInt(minTextField.getText()),
                        Integer.parseInt(maxTextField.getText())                            
                );
                Main.inventory.addPart(inhousePart);
            } else {
                outsourcedPart = new OutsourcedPart(
                        companyTextField.getText(),
                        partNameTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Integer.parseInt(inventoryTextField.getText()),
                        Integer.parseInt(minTextField.getText()),
                        Integer.parseInt(maxTextField.getText())
                );
                Main.inventory.addPart(outsourcedPart);
            }
        }
        Util.getStageFromActionEvent(event).close();
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
    
    public void initialize() {
        if(!isModifyPartView) {
            partIdTextField.setText(Integer.toString(Part.getNextPartID()));
        }
        Util.setFocusListenerForCurrencyFormat(priceTextField);
    }
    
    protected void initData(Part part) {
        isModifyPartView = true;
        partIdTextField.setText(Integer.toString(part.getPartID()));
        partNameTextField.setText(part.getName());
        inventoryTextField.setText(Integer.toString(part.getInStock()));
        priceTextField.setText(NumberFormat.getCurrencyInstance().format(part.getPrice()));
        minTextField.setText(Integer.toString(part.getMin()));
        maxTextField.setText(Integer.toString(part.getMax()));
        
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
    }
}
