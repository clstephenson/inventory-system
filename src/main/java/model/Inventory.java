package main.java.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class used to hold collections of the part and product inventory
 * @author Chris Stephenson
 */
public class Inventory {
    
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    
    /**
     * Constructor used to initialize the inventory object
     */
    public Inventory() {
        products.clear();
        allParts.clear();
    }
    
    /**
     * Add a product to the inventory
     * @param product
     */
    public void addProduct(Product product) {
        products.add(product);
    }
    
    /**
     * Remove a product from the inventory
     * @param productID ID of the product to be removed
     * @return true if the product was successfully removed
     */
    public boolean removeProduct(int productID) {
        return products.remove(lookupProduct(productID));
    }
    
    /**
     * Find a product in the inventory by product ID
     * @param productID ID of product to find
     * @return Product object with a matching product ID
     */
    public Product lookupProduct(int productID) {
        for(Product p : products) {
            if(p.getProductID() == productID)
                return p;
        }
        return null;
    }
    
//    public void updateProduct(int productID) {
//        // method not used - see ProductController for equivilent functionality
//    }

    /**
     * Add a part to the inventory
     * @param part
     */    
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    /**
     * Remove a part from the inventory
     * @param partID ID of the part to be removed
     * @return true if the part was successfully removed
     */
    public boolean deletePart(int partID) {
        return allParts.remove(lookupPart(partID));
    }
    
    /**
     * Find a part in the inventory by part ID
     * @param partID ID of part to find
     * @return Part object with a matching part ID
     */
    public Part lookupPart(int partID) {
        for(Part p : allParts) {
            if(p.getPartID() == partID)
                return p;
        }
        return null;
    }
    
//    public void updatePart(int partID) {
//        // method not used - see PartController for equivilent functionality
//    }

    /**
     * get the list of all parts in the inventory
     * @return ObservableList of Parts
     */    
    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    /**
     * get the list of all products in the inventory
     * @return ObservableList of Products
     */  
    public ObservableList<Product> getAllProducts() {
        return products;
    }
    
}
