package main.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutsourcedPart extends Part {

    private StringProperty companyName = new SimpleStringProperty();
    
    public OutsourcedPart() {
        super();
        this.setCompanyName("");
    }

    public OutsourcedPart(String companyName, int partID, String name, double price, int inStock, int min, int max) {
        super(partID, name, price, inStock, min, max);
        this.companyName.set(companyName);
    }
        
    public final String getCompanyName() {
        return companyName.get();
    }

    public final void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
        
}