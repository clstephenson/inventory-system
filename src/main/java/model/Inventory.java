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
        // todo: implement romoveProduct
        throw new RuntimeException("not implemented");
    }
    
    public Product lookupProduct(int productID) {
        // todo: implement lookupProduct
        throw new RuntimeException("not implemented");
    }
    
    public void updateProduct(int productID) {
        // todo: implement updateProduct
        throw new RuntimeException("not implemented");
    }
    
    public void addPart(Part part) {
        // todo: implement addPart
        allParts.add(part);
    }
    
    public boolean deletePart(int partID) {
        // todo: implement deletePart
        throw new RuntimeException("not implemented");
    }
    
    public Part lookupPart(int partID) {
        // todo: implement lookupPart
        throw new RuntimeException("not implemented");
    }
    
    public void updatePart(int partID) {
        // todo: implement updatePart
        throw new RuntimeException("not implemented");
    }
    
    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public ObservableList<Product> getAllProducts() {
        return products;
    }
}
