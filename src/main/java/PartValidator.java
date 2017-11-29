package main.java;

import main.java.model.Part;

/**
 *
 * @author Chris
 */
public class PartValidator extends Validator {
    
    private Part part;
    
    public PartValidator(Part part) {
        super();
        this.part = part;
    }
    
    public String validate() {
        super.validateInventoryLevelBetweenMinAndMaxInclusive(part.getInStock(), part.getMin(), part.getMax());
        super.validateMaxNotLessThanMin(part.getMin(), part.getMax());
        super.validateMinNotGreaterThanMax(part.getMin(), part.getMax());
        
        String message = super.getMessageAsString();
        return message.length() == 0 ? null : message;
    }
}
