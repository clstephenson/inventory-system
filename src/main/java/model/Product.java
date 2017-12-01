package main.java.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    
    private static int lastProductID = 0;   
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private IntegerProperty productID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty inStock = new SimpleIntegerProperty();
    private IntegerProperty min = new SimpleIntegerProperty();
    private IntegerProperty max = new SimpleIntegerProperty();
    
    public Product() {
        associatedParts.clear();
        setProductID(getNextProductID());
        setName("");
        setPrice(0.00);
        setInStock(0);
        setMin(0);
        setMax(0);
        
        lastProductID++;
    }
    
    public Product(String name, 
            double price, int inStock, int min, int max) {
        setProductID(getNextProductID());
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
        
        lastProductID++;
    }
    
    public final int getProductID() {
        return productID.get();
    }

    public final void setProductID(int productID) {
        this.productID.set(productID);
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public final double getPrice() {
        return price.get();
    }

    public final void setPrice(double price) {
        this.price.set(price);
    }

    public final int getInStock() {
        return inStock.get();
    }

    public final void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public final int getMin() {
        return min.get();
    }

    public final void setMin(int min) {
        this.min.set(min);
    }

    public final int getMax() {
        return max.get();
    }

    public final void setMax(int max) {
        this.max.set(max);
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partID) {
        for(Part p : associatedParts) {
            if(p.getPartID() == partID)
                return associatedParts.remove(p);
        }
        return false;
    }
    
    public Part lookupAssociatedPart(int partID) {
        for(Part p : associatedParts) {
            if(p.getPartID() == partID)
                return p;
        }
        return null;
    }
    
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    public boolean hasAssociatedParts() {
        return !associatedParts.isEmpty();
    }
    
    public static final int getNextProductID() {
        return lastProductID + 1;
    }
    
}
