package cs2030.simulator;

/**
 * GreedyCustomer class represents a customer who is greedy.
 */
public class GreedyCustomer extends Customer {

    /**
     * Creates a new greedy customer with an id.
     * 
     * @param id The id of the greedy customer.
     */
    GreedyCustomer(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return this.getID() + "(greedy)";
    }

}