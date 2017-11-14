package main.java.model;

import javafx.beans.property.IntegerProperty;

public class InhousePart extends Part {

    private IntegerProperty machineID;
    
    public InhousePart() {
        super();
        this.setMachineID(0);
    }

    public InhousePart(int machineID, int partID, String name, double price, int inStock, int min, int max) {
        super(partID, name, price, inStock, min, max);
        this.machineID.set(machineID);
    }
        
    public final int getMachineID() {
        return machineID.get();
    }

    public final void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
        
}
