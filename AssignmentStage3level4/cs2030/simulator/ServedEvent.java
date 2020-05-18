package cs2030.simulator;

import java.util.Queue;

/**
 * ServedEvent class that represents a CustomerEvent when a customer is being
 * served.
 */

public class ServedEvent extends CustomerEvent {

    /**
     * The completion time of the service.
     */

    private double doneTime;

    /**
     * The server that is serving the customer.
     */

    private Server sv;

    /**
     * Creates a new ServedEvent with the time of the event, serviceTime, customer
     * that is served and the server serving the customer.
     * 
     * @param time        The time of that the customer is being served.
     * @param serviceTime The duration of the entire service.
     * @param cust        The customer that is being served.
     * @param sv          The server that is serving the customer.
     */

    ServedEvent(double time, double serviceTime, Customer cust, Server sv) {
        this.time = time;
        this.doneTime = serviceTime + time;
        this.cust = cust;
        this.sv = sv.serve(cust);
    }

    /**
     * Creates a new ServedEvent with the time of the event, serviceTime, customer
     * that is served, the server serving the customer and the server's updated
     * waiting list.
     * 
     * @param time        The time of that the customer is being served.
     * @param serviceTime The duration of the entire service.
     * @param cust        The customer that is being served.
     * @param sv          The server that is serving the customer.
     * @param waiting     The updated waiting list of the server.
     */

    ServedEvent(double time, double serviceTime, Customer cust, Server sv, 
            Queue<Customer> waiting) {
        this.time = time;
        this.doneTime = serviceTime + time;
        this.cust = cust;
        this.sv = ((HumanServer) sv).serve(cust, waiting);
    }

    public double getDoneTime() {
        return doneTime;
    }

    public Server getServer() {
        return sv;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + 
                this.cust.toString() + " served by " + this.sv.toString();
    }

}