package main.java;

/**
 *
 * @author Chris
 */
public class PartValidator extends Validator {
    
    private final String name;
    private final String min;
    private final String max;
    private final String inventory;
    private final String price;
    private final String machineID;
    
    public PartValidator(String name, String min, String max, String inventory, String price, String machineID) {
        super();
        this.name = name;
        this.min = min;
        this.max = max;
        this.inventory = inventory;
        this.machineID = machineID;
        this.price = price.replace(",", "").replace("$", ""); //remove , and $ from price text
    }
    
    @Override
    public boolean validate() {
        if(areValuesPresentAndCorrectTypes()) {
            // only run these tests if we have valid values for each required field
            super.validateInventoryLevelBetweenMinAndMaxInclusive(inventory, min, max);
            super.validateMaxNotLessThanMin(min, max);
            super.validateMinNotGreaterThanMax(min, max);
        }        
        return super.isValid();
    }
    
    @Override
    protected boolean areValuesPresentAndCorrectTypes() {
        boolean ok = false;
        if(machineID == null) {
            if(super.validateIsNotEmpty(name, price, inventory, min, max) &&
                    super.validateIsNumeric(min, max, inventory, price)) {
                ok = true;
            }
        } else {
            if(super.validateIsNotEmpty(name, price, inventory, min, max) &&
                    super.validateIsNumeric(min, max, inventory, price, machineID)) {
                ok = true;
            }
        }
        return ok;
    }
}
