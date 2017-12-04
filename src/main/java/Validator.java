package main.java;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Abstract class to provide data validation.  This class should only be instantiated by it's subclasses.  
 * To create a new validator object, use the getValidator method to get the correct Validator object.
 * @author Chris Stephenson
 */
public abstract class Validator {
    
    private StringJoiner message;
 
    /**
     * enumeration of validator types
     */
    public enum ValidatorTypes {PART, PRODUCT}
    
    /**
     * This method should be called to get the correct Validator subclass object.  The object returned depends on the type parameter.
     * @param type determines the type of Validator object that will be created and returned.
     * @param name
     * @param min
     * @param max
     * @param inventory
     * @param price
     * @param machineID can be null if not applicable to data being validated.
     * @param productParts can be null if not applicable to data being validated.
     * @return either a PartValidator object or ProductValidator object, or null if not a valid type.
     */
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
    
    /**
     * constructor should be called by a subclass and initializes the validation message. 
     */
    protected Validator() {
        message = new StringJoiner("\n");        
    }
    
    /**
     * Abstract method that must be implemented by each subclass. Run the validation methods. 
     * @return true if fields are valid and false if any of the validations fail.
     */
    public abstract boolean validate();
    
    /**
     * Abstract method that must be implemented by each subclass. Check that required 
     * fields have values and fields are of the correct data type (i.e. numeric).
     * @return true if fields are valid and false if any of the validations fail.
     */
    protected abstract boolean areValuesPresentAndCorrectTypes();
    
    /**
     * Check that the max value is not less than the min value.
     * @param min numeric string
     * @param max numeric string
     */
    protected void validateMaxNotLessThanMin(String min, String max) {
        if(Integer.parseInt(max) < Integer.parseInt(min)) {
            message.add("Max inventory value cannot be less than min.");
        }
    }
    
    /**
     * Check that the min value is not more than the max value.
     * @param min numeric string
     * @param max numeric string
     */
    protected void validateMinNotGreaterThanMax(String min, String max) {
        if(Integer.parseInt(min) > Integer.parseInt(max)) {
            message.add("Min inventory value cannot be greater than max.");
        }
    }
    
    /**
     * Check that the inventory value is equal to, or between, the min and max values.
     * @param inventory numeric string
     * @param min numeric string
     * @param max numeric string
     */
    protected void validateInventoryLevelBetweenMinAndMaxInclusive(String inventory, String min, String max) {
        if(Integer.parseInt(inventory) < Integer.parseInt(min) || 
                Integer.parseInt(inventory) > Integer.parseInt(max)) {
            message.add("Inventory stock level must be between the min and max values.");
        }
    }   
    
    /**
     * Validate if each field in a list of string parameters is numeric.
     * @param fields list of string fields to be checked.
     * @return true if validation passes, false if it fails.
     */
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
    
    /**
     * Validate if each field in a list of string parameters has a value that is 
     * not null or "".
     * @param fields list of string fields to be checked.
     * @return true if validation passes, false if it fails.
     */
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
        
    /**
     * Add a message to the validation messages.
     * @param message message to add
     */
    protected void addToMessage(String message) {
        this.message.add(message);
    }
    
    /**
     * method to get the message object.
     * @return
     */
    protected StringJoiner getMessage() {
        return message;
    }
    
    /**
     * get the entire validation message as a String that can be displayed.
     * @return
     */
    public String getMessageAsString() {
        return message.toString();
    }
    
    /**
     * check if there are any validation errors.
     * @return true if valid and false if there are any messages found in message.
     */
    protected boolean isValid() {
        return message.length() == 0;
    }        
}
