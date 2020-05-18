package cs2030.simulator;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * SelfCheck class represents a server that is a self-check counter.
 */

public class SelfCheck extends Server {

    /**
     * A static waiting queue to share with all the self-check servers.
     */

    private static Queue<Customer> selfCheckQueue = new LinkedList<>();

    /**
     * Creates a default SelfCheck counter with an id.
     * 
     * @param id The id of the SelfCheck counter.
     */

    SelfCheck(int id) {
        this.id = id;
        this.cust = Optional.empty();
    }

    /**
     * Creates a new SelfCheck counter with an id and the customer that its serving.
     * 
     * @param id   The id of the SelfCheck counter.
     * @param cust The customer that is being served.
     */

    SelfCheck(int id, Customer cust) {
        this.id = id;
        this.cust = Optional.of(cust);
    }

    /**
     * Checks if a customer can be added to the waiting queue. (i) The number of
     * customers in the waiting queue has not exceed the maximum queue size.
     * 
     * @return True/False
     */

    public static boolean canWait() {
        return selfCheckQueue.size() < maxQueue;
    }

    public static Queue<Customer> getWaitingList() {
        return selfCheckQueue;
    }

    /**
     * Updates the SelfCheck counter after it is done serving.
     * 
     * @return An updated SelfCheck counter that is not serving any customer.
     */

    public SelfCheck doneServing() {
        return new SelfCheck(this.id);
    }

    /**
     * Updates the SelfCheck counter to serve a customer.
     * 
     * @return An updated SelfCheck counter that is serving a new customer.
     */

    public SelfCheck serve(Customer cu) {
        return new SelfCheck(this.id, cu);
    }

    @Override
    public String toString() {
        return "self-check " + this.getID();
    }

}