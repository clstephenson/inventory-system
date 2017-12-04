package main.java.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Subclass of Part class, adding constructors and machineID property.
 * @author Chris Stephenson
 */
public class InhousePart extends Part {

    private IntegerProperty machineID = new SimpleIntegerProperty();
    
    /**
     * Constructor, taking no parameters, used to create an empty InhousePart object.
     */
    public InhousePart() {
        super();
        this.setMachineID(0);
    }

    /**
     * Constructor, used to create an InhousePart object by passing in all of the part data.
     * @param machineID
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     */
    public InhousePart(int machineID, String name, double price, int inStock, int min, int max) {
        super(name, price, inStock, min, max);
        this.machineID.set(machineID);
    }
        
    /**
     * get the value from the machineID property
     * @return the machine ID
     */
    public final int getMachineID() {
        return machineID.get();
    }

    /**
     * set the value of the machineID property
     * @param machineID
     */
    public final void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
        
}
