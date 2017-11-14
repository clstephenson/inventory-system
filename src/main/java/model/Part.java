package main.java.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {
    
    private IntegerProperty partID;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;

    public Part() {
        this.setPartID(0);
        this.setName("");
        this.setPrice(0.00);
        this.setInStock(0);
        this.setMin(0);
        this.setMax(0);
    }
    
    public Part(int partID, String name, double price, int inStock, int min, int max) {
        this.setPartID(partID);
        this.setName(name);
        this.setPrice(price);
        this.setInStock(inStock);
        this.setMin(min);
        this.setMax(max);
    }

    public final int getPartID() {
        return partID.get();
    }

    public final void setPartID(int partID) {
        this.partID.set(partID);
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
    
}
