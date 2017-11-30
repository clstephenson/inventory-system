package main.java;

/**
 *
 * @author Chris
 */
public class PartValidator extends Validator {
    
    private String name;
    private String min;
    private String max;
    private String inventory;
    private String price;
    
    public PartValidator(String name, String min, String max, String inventory, String price) {
        super();
        this.name = name;
        this.min = min;
        this.max = max;
        this.inventory = inventory;
        this.price = price.contains("$") ? price.substring(1) : price;   
        //TODO:  need to remove $ from price text to match numeric regex in validateIsNumeric!
    }
    
    public boolean validate() {
        if(areValuesPresentAndCorrectTypes()) {
            // only run these tests if we have valid values for each required field
            super.validateInventoryLevelBetweenMinAndMaxInclusive(inventory, min, max);
            super.validateMaxNotLessThanMin(min, max);
            super.validateMinNotGreaterThanMax(min, max);
        }        
        return super.hasValidationErrors();
    }
    
    protected boolean areValuesPresentAndCorrectTypes() {
        return super.validateIsNumeric(min, max, inventory, price);
    }
}
