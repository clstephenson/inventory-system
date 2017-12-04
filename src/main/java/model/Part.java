package main.java.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Abstract Part class is parent to InhousePart and OutsourcedPart
 * @author Chris Stephenson
 */
public abstract class Part {
    
    private static int lastPartID = 0; // holds the last used part id
    private IntegerProperty partID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty inStock = new SimpleIntegerProperty();
    private IntegerProperty min = new SimpleIntegerProperty();
    private IntegerProperty max = new SimpleIntegerProperty();

    /**
     * Construct an empty Part object
     */
    public Part() {
        setPartID(getNextPartID());
        setName("");
        setPrice(0.00);
        setInStock(0);
        setMin(0);
        setMax(0);
        
        lastPartID++;
    }
    
    /**
     * Constructor, used to create an Part object by passing in all of the part data.
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     */
    public Part(String name, double price, int inStock, int min, int max) {
        setPartID(getNextPartID());
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
        
        lastPartID++;
    }

    /**
     * getter for the value of the part ID property
     * @return
     */
    public final int getPartID() {
        return partID.get();
    }

    /**
     * setter for the value of the part ID property
     * @param partID
     */
    public final void setPartID(int partID) {
        this.partID.set(partID);
    }

    /**
     * getter for the value of the name property
     * @return
     */
    public final String getName() {
        return name.get();
    }

    /**
     * setter for the value of the name property
     * @param name
     */
    public final void setName(String name) {
        this.name.set(name);
    }

    /**
     * getter for the value of the price property
     * @return
     */
    public final double getPrice() {
        return price.get();
    }

    /**
     * setter for the value of the price property
     * @param price
     */
    public final void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * getter for the value of the inventory stock property
     * @return
     */
    public final int getInStock() {
        return inStock.get();
    }

    /**
     * setter for the value of the inventory stock property
     * @param inStock
     */
    public final void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    /**
     * getter for the value of the min property
     * @return
     */
    public final int getMin() {
        return min.get();
    }

    /**
     * setter for the value of the min property
     * @param min
     */
    public final void setMin(int min) {
        this.min.set(min);
    }

    /**
     * getter for the value of the max property
     * @return
     */
    public final int getMax() {
        return max.get();
    }

    /**
     * setter for the value of the max property
     * @param max
     */
    public final void setMax(int max) {
        this.max.set(max);
    }
    
    /**
     * Add one to the last used part ID
     * @return
     */
    public static final int getNextPartID() {
        return lastPartID + 1;
    }
    
}
