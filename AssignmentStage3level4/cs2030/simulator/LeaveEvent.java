package cs2030.simulator;

/**
 * LeaveEvent class that represents a CustomerEvent when a customer leaves.
 */

public class LeaveEvent extends CustomerEvent {

    /**
     * Creates a new LeaveEvent with the time of the event and the customer that is
     * leaving.
     * 
     * @param time The time when the customer leaves.
     * @param cust The customer that is leaving.
     */
    LeaveEvent(double time, Customer cust) {
        this.time = time;
        this.cust = cust;
    }

    public Customer getCustomer() {
        return this.cust;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust.toString() + " leaves";
    }

}