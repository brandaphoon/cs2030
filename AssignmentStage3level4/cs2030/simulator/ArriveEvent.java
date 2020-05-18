package cs2030.simulator;

/**
 * ArriveEvent class represents a CustomerEvent when a customer arrives.
 */

public class ArriveEvent extends CustomerEvent {

    /**
     * Creates a new ArriveEvent with the time the customer arrives and the
     * customer.
     * 
     * @param time The time of arrival.
     * @param cust The customer that arrived.
     */

    ArriveEvent(double time, Customer cust) {
        this.time = time;
        this.cust = cust;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust.toString() + " arrives";
    }

}