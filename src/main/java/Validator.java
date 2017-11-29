/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.util.StringJoiner;
import main.java.model.Part;

/**
 *
 * @author Chris
 */
public abstract class Validator {
    
    private StringJoiner message;
    
    public static Validator getValidator(Object obj) {
        if(obj instanceof Part) {
            return new PartValidator((Part)obj);
        }
        return null;
    }
    
    protected Validator() {
        message = new StringJoiner("\n");
    }
    
    public abstract String validate();
    
    protected void validateMaxNotLessThanMin(int min, int max) {
        if(max < min) {
            message.add("Max inventory value cannot be less than min.");
        }
    }
    
    protected void validateMinNotGreaterThanMax(int min, int max) {
        if(min > max) {
            message.add("Min inventory value cannot be greater than max.");
        }
    }
    
    protected void validateInventoryLevelBetweenMinAndMaxInclusive(int inventory, int min, int max) {
        if(inventory < min || inventory > max) {
            message.add("Min inventory value cannot be greater than max.");
        }
    }    
    
    protected StringJoiner getMessage() {
        return message;
    }
    
    protected String getMessageAsString() {
        return message.toString();
    }
        
}
