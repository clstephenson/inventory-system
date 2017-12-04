package main.java;

import java.util.ArrayList;
import main.java.model.Part;

/**
 * The ProductValidator class extends the Validator to add specific validation methods and fields for Product data
 * @author Chris Stephenson
 */
public class ProductValidator extends Validator {
    
    private final String name;
    private final String min;
    private final String max;
    private final String inventory;
    private final String price;
    private final ArrayList<Part> productParts;
    
    /**
     * Constructor method to create a ProductValidator object.  All parameters are passed as strings, except for productParts.
     * @param name
     * @param min
     * @param max
     * @param inventory
     * @param price
     * @param productParts ArrayList containing a list of Part objects
     */
    public ProductValidator(String name, String min, String max, String inventory, 
            String price, ArrayList<Part> productParts) {
        super();
        this.name = name;
        this.min = min;
        this.max = max;
        this.inventory = inventory;
        this.price = price.replace(",", "").replace("$", ""); //remove , and $ from price text  
        this.productParts = productParts;
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
            validateProductHasAtLeastOnePart();
            validatePriceCannotBeLessThanCostOfParts();
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
        if(super.validateIsNotEmpty(name, price, inventory) &&
                super.validateIsNumeric(min, max, inventory, price)) {
            ok = true;
        }
        return ok;
    }
    
    private void validateProductHasAtLeastOnePart() {        
        if(productParts.isEmpty()) {
            super.addToMessage("A product must have at least one associated part.");
        }
    }
    
    private void validatePriceCannotBeLessThanCostOfParts() {
        double costOfParts = 0.00;
        for(Part p : productParts) {
            costOfParts += p.getPrice();
        }        
        if(Double.parseDouble(price) < costOfParts) {
            super.addToMessage("Product price cannot be less than its combined cost of parts.");
        }
    }
}
