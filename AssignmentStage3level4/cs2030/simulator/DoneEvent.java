package cs2030.simulator;

/**
 * DoneEvent class represents a CustomerEvent when a customer is done being
 * served.
 */

public class DoneEvent extends CustomerEvent {

    /**
     * The server that served the customer.
     */

    private final Server sv;

    /**
     * Creates a new DoneEvent with the time, customer and server.
     * 
     * @param time The time of the DoneEvent.
     * @param cust The customer of that has been finish serving.
     * @param sv   The server that served the customer.
     */
    DoneEvent(double time, Customer cust, Server sv) {
        this.time = time;
        this.cust = cust;
        this.sv = sv;
    }

    public Server getServer() {
        return sv;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + 
                    this.cust.toString() + " done serving by " + this.sv.toString();
    }

}