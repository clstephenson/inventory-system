package main.java;

import main.java.model.Part;
import main.java.model.Product;

/**
 *
 * @author Chris
 */
public class ProductValidator extends Validator {
    
    private Product product;
    
    public ProductValidator(Product product) {
        super();
        this.product = product;
    }
    
    public String validate() {
        super.validateInventoryLevelBetweenMinAndMaxInclusive(product.getInStock(), product.getMin(), product.getMax());
        super.validateMaxNotLessThanMin(product.getMin(), product.getMax());
        super.validateMinNotGreaterThanMax(product.getMin(), product.getMax());
        validateProductHasAtLeastOnePart();
        validatePriceCannotBeLessThanCostOfParts();
        
        String message = super.getMessageAsString();
        return message.length() == 0 ? null : message;
    }
    
    private void validateProductHasAtLeastOnePart() {        
        if(!product.hasAssociatedParts()) {
            super.addToMessage("A product must have at least one associated part.");
        }
    }
    
    private void validatePriceCannotBeLessThanCostOfParts() {
        double costOfParts = 0.00;
        for(Part p : product.getAssociatedParts()) {
            costOfParts += p.getPrice();
        }        
        if(product.getPrice() < costOfParts) {
            super.addToMessage("Product price cannot be less than its combined cost of parts.");
        }
    }
}
