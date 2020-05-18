package cs2030.simulator;

/**
 * Customer class represents a customer.
 */

public class Customer {

    /**
     * The id of the customer.
     */
    
    private final int id;

    /**
     * Creates a new customer.
     * 
     * @param id The id of the customer.
     */

    Customer(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getID());
    }

}