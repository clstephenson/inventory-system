/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 *
 * @author Chris Stephenson
 */
public abstract class Validator {
    
    private StringJoiner message;
    
 
    public enum ValidatorTypes {PART, PRODUCT}
    
    public static Validator getValidator(ValidatorTypes type, String name, String min, 
            String max, String inventory, String price, String machineID, ArrayList productParts) {
        if(type == ValidatorTypes.PART) {
            return new PartValidator(name, min, max, inventory, price, machineID);
        }
        else if(type == ValidatorTypes.PRODUCT) {
            return new ProductValidator(name, min, max, inventory, price, productParts);
        }
        return null;
    }
    
    protected Validator() {
        message = new StringJoiner("\n");        
    }
    
    public abstract boolean validate();
    
    protected abstract boolean areValuesPresentAndCorrectTypes();
    
    protected void validateMaxNotLessThanMin(String min, String max) {
        if(Integer.parseInt(max) < Integer.parseInt(min)) {
            message.add("Max inventory value cannot be less than min.");
        }
    }
    
    protected void validateMinNotGreaterThanMax(String min, String max) {
        if(Integer.parseInt(min) > Integer.parseInt(max)) {
            message.add("Min inventory value cannot be greater than max.");
        }
    }
    
    protected void validateInventoryLevelBetweenMinAndMaxInclusive(String inventory, String min, String max) {
        if(Integer.parseInt(inventory) < Integer.parseInt(min) || 
                Integer.parseInt(inventory) > Integer.parseInt(max)) {
            message.add("Inventory stock level must be between the min and max values.");
        }
    }   
    
    protected boolean validateIsNumeric(String ... fields) {
        boolean ok = true;
        for(String field : fields) {
            if(!field.isEmpty() && !field.matches("^[0-9]+\\.?[0-9]*$")) {
                ok = false;
            }
        }
        if(!ok) message.add("Min, Max, Inventory Stock, Price and Machine ID must be numeric.");
        return ok;
    }
    
    protected boolean validateIsNotEmpty(String ... fields) {
        boolean ok = true;
        for(String field : fields) {
            if(field == null || field.isEmpty()) {
                ok = false;
            }
        }
        if(!ok) message.add("Product/Part must have a name, price, and inventory level.");
        return ok;
    }
        
    protected void addToMessage(String message) {
        this.message.add(message);
    }
    
    protected StringJoiner getMessage() {
        return message;
    }
    
    public String getMessageAsString() {
        return message.toString();
    }
    
    protected boolean isValid() {
        return message.length() == 0;
    }
        
}
