package main.java.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class containing product data along with the product's associated parts
 * @author Chris Stephenson
 */
public class Product {
    
    private static int lastProductID = 0; // holds the last used product id
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private IntegerProperty productID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty inStock = new SimpleIntegerProperty();
    private IntegerProperty min = new SimpleIntegerProperty();
    private IntegerProperty max = new SimpleIntegerProperty();
    
    /**
     * Construct an empty Product object
     */
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
    
    /**
     * Constructor, used to create a Product object by passing in all of the product data.
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     */
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
    
    /**
     * getter for the value of the product ID property
     * @return
     */
    public final int getProductID() {
        return productID.get();
    }

    /**
     * setter for the value of the product ID property
     * @param productID
     */
    public final void setProductID(int productID) {
        this.productID.set(productID);
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
     * Add a Part object to the product's associated parts list
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    /**
     * Remove a Part from the product's associated parts list
     * @param partID ID of part to remove
     * @return true if removal was successful
     */
    public boolean removeAssociatedPart(int partID) {
        for(Part p : associatedParts) {
            if(p.getPartID() == partID)
                return associatedParts.remove(p);
        }
        return false;
    }
    
    /**
     * lookup a Part in the product's associated parts list by ID
     * @param partID ID of part to find
     * @return the found Part object or null if no match is found
     */
    public Part lookupAssociatedPart(int partID) {
        for(Part p : associatedParts) {
            if(p.getPartID() == partID)
                return p;
        }
        return null;
    }
    
    /**
     * getter for the list of associated parts
     * @return
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    /**
     * check if the product has any associated parts
     * @return
     */
    public boolean hasAssociatedParts() {
        return !associatedParts.isEmpty();
    }
    
    /**
     * Add one to the last used product ID
     * @return
     */
    public static final int getNextProductID() {
        return lastProductID + 1;
    }
    
}
