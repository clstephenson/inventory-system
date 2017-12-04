package main.java;

/**
 * The PartValidator class extends the Validator to add specific validation methods and fields for Part data
 * @author Chris Stephenson
 */
public class PartValidator extends Validator {
    
    private final String name;
    private final String min;
    private final String max;
    private final String inventory;
    private final String price;
    private final String machineID;
    
    /**
     * Constructor method to create a PartValidator object.  All parameters are passed as strings.
     * @param name
     * @param min
     * @param max
     * @param inventory
     * @param price
     * @param machineID pass null for this parameter if the part is an outsourced part.
     */
    public PartValidator(String name, String min, String max, String inventory, String price, String machineID) {
        super();
        this.name = name;
        this.min = min;
        this.max = max;
        this.inventory = inventory;
        this.machineID = machineID;
        this.price = price.replace(",", "").replace("$", ""); //remove , and $ from price text
    }
    
    /**
     * Run the validation methods.
     * @return true if fields are valid and false if any of the validations fail.
     */
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
    
    /**
     * Check that required fields have values and fields are of the correct data type (i.e. numeric).
     * @return true if fields are valid and false if any of the validations fail.
     */
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
