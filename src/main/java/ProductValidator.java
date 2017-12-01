package main.java;

import java.util.ArrayList;
import main.java.model.Part;

/**
 *
 * @author Chris
 */
public class ProductValidator extends Validator {
    
    private final String name;
    private final String min;
    private final String max;
    private final String inventory;
    private final String price;
    private final ArrayList<Part> productParts;
    
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
