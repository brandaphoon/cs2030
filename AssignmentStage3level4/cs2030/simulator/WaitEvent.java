package cs2030.simulator;

/**
 * WaitEvent class represents a CustomerEvent when the customer is waiting to be
 * served.
 */
public class WaitEvent extends CustomerEvent {

    /**
     * The server that the customer is waiting on.
     */

    private Server sv;

    /**
     * Creates a new WaitEvent with the time, the customer that is waiting and the
     * server. (i) If its a human server, the customer will be added to it's waiting
     * list. (ii) If its a self-check, the customer will be added to the shared
     * static waiting list.
     * 
     * @param time The time that the customer begins to wait.
     * @param cust The customer that is waiting.
     * @param sv   The server that the customer is waiting on.
     */
    WaitEvent(double time, Customer cust, Server sv) {
        this.time = time;
        this.cust = cust;
        if (sv instanceof HumanServer) {
            this.sv = ((HumanServer) sv).wait(cust);
        } else {
            this.sv = sv;
            SelfCheck.getWaitingList().add(cust);
        }

    }

    public Server getServer() {
        return sv;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + 
                this.cust.toString() + " waits to be served by " + this.sv.toString();
    }
}