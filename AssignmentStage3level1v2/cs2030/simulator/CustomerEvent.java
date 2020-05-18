package cs2030.simulator;

public abstract class CustomerEvent extends Event {

    protected Customer cust;

    public Customer getCustomer() {
        return this.cust;

    }
    
}