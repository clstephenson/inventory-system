package main.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Subclass of Part class, adding constructors and companyName property.
 * @author Chris Stephenson
 */
public class OutsourcedPart extends Part {

    private StringProperty companyName = new SimpleStringProperty();
    
    /**
     * Constructor, taking no parameters, used to create an empty OutsourcedPart object.
     */
    public OutsourcedPart() {
        super();
        this.setCompanyName("");
    }

    /**
     * Constructor, used to create an OutsourcedPart object by passing in all of the part data.
     * @param companyName
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     */
    public OutsourcedPart(String companyName, String name, double price, int inStock, int min, int max) {
        super(name, price, inStock, min, max);
        this.companyName.set(companyName);
    }
        
    /**
     * get the value from the companyName property
     * @return the company name
     */
    public final String getCompanyName() {
        return companyName.get();
    }

    /**
     * set the value of the companyName property
     * @param companyName
     */
    public final void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
        
}