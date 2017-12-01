package main.java.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    
    public Inventory() {
        products.clear();
        allParts.clear();
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public boolean removeProduct(int productID) {
        return products.remove(lookupProduct(productID));
    }
    
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
    
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    public boolean deletePart(int partID) {
        return allParts.remove(lookupPart(partID));
    }
    
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
    
    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public ObservableList<Product> getAllProducts() {
        return products;
    }
    
}
