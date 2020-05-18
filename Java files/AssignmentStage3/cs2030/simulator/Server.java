package cs2030.simulator;

import java.util.ArrayList;
/**
 * Server class represents a Server.
 */

public class Server {
    
    /**
     * The id of the customer.
     */

    private final int id;

    /**
     * The customer that the server is serving.
     */

    private final Customer cust;
    
    /**
     * The customer that is waiting to be served by the server.
     */

    //private final Customer waiting;
    private final ArrayList<Customer> waiting;
    
    /**
     * The service time required by the server to complete a service.
     */

    private static final double serviceTime = 1.0;
    
    /**
     * The time that the server complete their service.
     */

    private final double completionTime;

    private final int max;

    /**
     * Creates a default new Server with a id that has no customers.
     * @param id of the server
     */
    
    public Server(int id, int max) {
        this.id = id;
        this.cust = null;
        this.waiting = null;
        this.completionTime = -1;
        this.max = max;
    }
    
    /**
     * Creates a new Server with a id, serving a customer, including the 
     * completionTime of the entire service.
     * @param id the id of the server, 
     * @param cust customer that the server is serving
     */
    
    public Server(int id, Customer cust, int max) {
        this.id = id;
        this.cust = cust;
        this.waiting = null;
        this.completionTime = this.serviceTime + cust.getTime();
        this.max = max;
    }

    /**
     * Creates a new Server with a id, current customer that is being served
     * and a customer that is waitng to be served with the completion time of
     * the service of the current customer.
     * @param id the id of the server, 
     * @param c1 the customer that the server is serving
     * @param c2 the customer that is waiting to be served
     */

    public Server(int id, Customer c1, ArrayList<Customer> waiting, int max) {
        this.id = id;
        this.cust = c1;
        this.waiting = waiting;
        this.completionTime = this.serviceTime + c1.getTime();
        this.max = max;
    }

    public int getID() {
        return this.id;
    }

    public Customer getCustomer() {
        return this.cust;
    }

    public ArrayList<Customer> getWaiting() {
        return this.waiting;
    }

    public double getCompletionTime() {
        return this.completionTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public int getMaxCustomers() {
        return max;
    }
    
    /**
     * Creates a new Server serving a customer (i) when the server is
     * available (ii) fit the preconditions to serve the input
     * customer.
     * @param cust the customer that is being served
     * @return A new updated server
     */

    public Server serve(Customer cust) {
        if (this.canServe(cust)) {
            return new Server(this.id, cust, this.max);
        } else {
            return null;
        }
    }


    /**
     * Creates a new updated server with a waiting customer when all
     * preconditions are met.
     * @param cust the customer that is going to be waiting to be served
     * @return A new updated server
     */

    public Server wait(Customer cust) {
        if (this.canWait()) {
            ArrayList<Customer> waiting = new ArrayList<>(this.waiting);
            waiting.add(cust);
            return new Server(this.id, this.getCustomer(), waiting, this.max);
        } else {
            return null;
        }
    }

    /**
     * Checks whether a customer can be served by
     * the server based on the customer's arrivalTime and the server's 
     * completionTime.
     * @param cust the customer that needs to be served
     * @return true/false if customer can be served by the server
     */
    
    public boolean canServe(Customer cust) {
        if (cust.getTime() >= this.completionTime) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether customer can be added to wait to be served by the server.
     * @return true/false if a customer can be added to wait to be served
     */

    public boolean canWait() {
        if (this.getWaiting() == null || this.getWaiting().size() < this.getMaxCustomers()) {
            return true;
        } else {
            return false;
        }
    }
           
}
