package cs2030.simulator;

/**
 * Server class represents a Server.
 */

import java.util.ArrayList;
import java.util.Optional;

public class Server {

    /**
     * The id of the customer.
     */

    private final int id;

    /**
     * The customer that the server is serving.
     */

    private final Optional<Customer> cust;

    /**
     * The customer that is waiting to be served by the server.
     */

    private final ArrayList<Customer> waiting;

    /**
     * The service time required by the server to complete a service.
     */

    private double serviceTime;

    /**
     * The time that the server complete their service.
     */

    private final double completionTime;

    private final int maxQueue;

    private final boolean isResting;

    /**
     * Creates a default new Server with a id that has no customers.
     * 
     * @param id of the server
     */

    public Server(int id, int maxQueue, double serviceTime) {
        this.id = id;
        this.cust = Optional.empty();
        this.waiting = new ArrayList<>();
        this.completionTime = -1;
        this.maxQueue = maxQueue;
        this.serviceTime = serviceTime;
        this.isResting = false;
    }

    /**
     * Creates a new Server with a id, serving a customer, including the
     * completionTime of the entire service.
     * 
     * @param id   the id of the server,
     * @param cust customer that the server is serving
     */

    public Server(int id, Customer cust, int maxQueue, double serviceTime) {
        this.id = id;
        this.cust = Optional.of(cust);
        this.waiting = new ArrayList<>();
        this.completionTime = serviceTime + cust.getTime();
        this.maxQueue = maxQueue;
        this.serviceTime = serviceTime;
        this.isResting = false;
    }

    /**
     * Creates a new Server with a id, current customer that is being served and a
     * customer that is waitng to be served with the completion time of the service
     * of the current customer.
     * 
     * @param id the id of the server,
     * @param c1 the customer that the server is serving
     * @param c2 the customer that is waiting to be served
     */

    public Server(int id, Customer c1, ArrayList<Customer> waiting, int maxQueue, double serviceTime) {
        this.id = id;
        this.cust = Optional.of(c1);
        this.waiting = waiting;
        this.completionTime = serviceTime + c1.getTime();
        this.maxQueue = maxQueue;
        this.serviceTime = serviceTime;
        this.isResting = false;
    }

    public Server(int id, Customer c1, ArrayList<Customer> waiting, int maxQueue, double serviceTime,
            double completionTime, boolean rest) {
        this.id = id;
        this.cust = Optional.of(c1);
        this.waiting = waiting;
        this.completionTime = completionTime;
        this.maxQueue = maxQueue;
        this.serviceTime = serviceTime;
        this.isResting = rest;
    }

    public int getID() {
        return this.id;
    }

    public Optional<Customer> getCustomer() {
        return this.cust;
    }

    public ArrayList<Customer> getWaiting() {
        return this.waiting;
    }

    public double getCompletionTime() {
        return this.completionTime;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }

    public int getMaxQueue() {
        return this.maxQueue;
    }

    public boolean isResting() {
        return isResting;
    }

    /**
     * Creates a new Server serving a customer (i) when the server is available (ii)
     * fit the preconditions to serve the input customer.
     * 
     * @param cust the customer that is being served
     * @return A new updated server
     */

    public Optional<Server> serve(Customer cust, double svTime) {
        if (this.canServe(cust) && !this.isResting) {
            return Optional.of(new Server(this.id, cust, this.maxQueue, svTime));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Creates a new updated server with a waiting customer when all preconditions,
     * are met.
     * 
     * @param cust the customer that is going to be waiting to be served
     * @return A new updated server
     */

    public Optional<Server> wait(Customer cust) {
        if (this.canWait()) {
            ArrayList<Customer> newWaiting = new ArrayList<Customer>();
            if (this.waiting != null) {
                newWaiting.addAll(this.waiting);
            }
            newWaiting.add(cust);
            return Optional.of(new Server(this.id, this.getCustomer().get(), newWaiting, this.maxQueue, this.serviceTime, this.completionTime, this.isResting));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks whether a customer can be served by the server based on the customer's
     * arrivalTime and the server's completionTime.
     * 
     * @param cust the customer that needs to be served
     * @return true/false if customer can be served by the server
     */

    public boolean canServe(Customer cust) {
        if (cust.getTime() > this.completionTime) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether customer can be added to wait to be served by the server.
     * 
     * @return true/false if a customer can be added to wait to be served
     */

    public boolean canWait() {
        if (this.getWaiting().size() < this.maxQueue) {
            return true;
        }
        return false;

    }

    public Server rest(double duration) {
        double completionTime = this.serviceTime + this.cust.get().getTime() + duration;
        return new Server(this.id, this.getCustomer().get(), this.waiting, this.maxQueue, this.serviceTime, completionTime, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Server) {
            Server newSv = (Server) o;
            return (newSv.getID() == this.getID());
        }
        return false;
    }

}
