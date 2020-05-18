package cs2030.simulator;

import java.util.Optional;

/**
 * Server class represents a server.
 */

public abstract class Server {

    /**
     * The id of the server.
     */

    protected int id;

    /**
     * A static maximum number of customers that can be in the waiting queue.
     */
    protected static int maxQueue;

    /**
     * An optional of customer that the server is serving.
     */

    protected Optional<Customer> cust;

    public int getID() {
        return this.id;
    }

    public Optional<Customer> getCustomer() {
        return this.cust;
    }

    /**
     * Update a server with the customer its serving.
     * 
     * @param cu The customer that is beinf served.
     * @return An updated Server.
     */

    public abstract Server serve(Customer cu);

    /**
     * Update a server that is done serving.
     * 
     * @return An updated Server;
     */

    public abstract Server doneServing();

    /**
     * Set the maximum number of customers allowed in the waiting queue for all
     * servers.
     * 
     * @param maxQueue The maximum number of customers in a waiting queue.
     */
    public static void setMaxQueue(int maxQueue) {
        Server.maxQueue = maxQueue;
    }

    public int getMaxQueue() {
        return Server.maxQueue;
    }

    /**
     * Checks if the server can serve a new customer.
     * 
     * @return True/False.
     */
    public boolean canServe() {
        return !this.cust.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Server) {
            return ((Server) o).getID() == this.getID();
        }
        return false;
    }

}