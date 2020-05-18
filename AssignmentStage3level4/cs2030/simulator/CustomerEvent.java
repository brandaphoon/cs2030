package cs2030.simulator;

/**
 * CustomerEvent abstract class represents an Event that involves a customer.
 */

public abstract class CustomerEvent extends Event {

    /**
     * The customer that is involved in an event.
     */

    protected Customer cust;

    public Customer getCustomer() {
        return this.cust;

    }

}