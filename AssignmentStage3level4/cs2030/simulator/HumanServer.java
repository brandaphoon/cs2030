package cs2030.simulator;

import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;

/**
 * HumanServer class represents a Server that is human.
 */

public class HumanServer extends Server {

    /**
     * The list of customers who are waiting in the queue.
     */

    private final Queue<Customer> waitingList;

    /**
     * True/False, if the human server is idle.
     */

    private final boolean isIdle;

    /**
     * True/False, if the human server is resting.
     */

    private final boolean isResting;

    /**
     * Creates a default human server with an id that is not serving a customer,
     * have no customers waiting in the queue, is idle and not resting.
     * 
     * @param id The id of the human server.
     */

    HumanServer(int id) {
        this.id = id;
        this.cust = Optional.empty();
        this.waitingList = new LinkedList<>();
        this.isIdle = true;
        this.isResting = false;
    }

    /**
     * Creates a human server with an id that is serving a customer, not idle, not
     * resting and no waiting customers.
     * 
     * @param id   The id of the human server.
     * @param cust The customer that the human server is serving.
     */

    HumanServer(int id, Customer cust) {
        this.id = id;
        this.cust = Optional.of(cust);
        this.waitingList = new LinkedList<>();
        this.isIdle = false;
        this.isResting = false;
    }

    /**
     * Creates a human server with an id that is not serving a customer, not
     * idle/idle depending on whether there are customers waiting in the queue, and
     * not resting.
     * 
     * @param id          The id of the human server.
     * @param waitingList The list of customers who are waiting in the queue.
     */

    HumanServer(int id, Queue<Customer> waitingList) {
        this.id = id;
        this.cust = Optional.empty();
        this.waitingList = waitingList;
        this.isIdle = (waitingList.size() == 0);
        this.isResting = false;
    }

    /**
     * Creates a human server with an id that is serving/not serving a customer, the
     * waiting list, not idle/idle, and not resting/resting.
     * 
     * @param id          The id of the human server.
     * @param waitingList The list of customers who are waiting in the queue.
     */

    HumanServer(int id, Optional<Customer> cust, Queue<Customer> waitingList, 
            boolean isIdle, boolean isResting) {
        this.id = id;
        this.cust = cust;
        this.waitingList = waitingList;
        this.isIdle = isIdle;
        this.isResting = isResting; // false
    }

    public Queue<Customer> getWaitingList() {
        return this.waitingList;
    }

    /**
     * Checking if the human server is idle.
     * 
     * @return True/False
     */
    public boolean isIdle() {
        return this.isIdle;
    }

    /**
     * Checking if the human server is resting.
     * 
     * @return True/False
     */

    public boolean isResting() {
        return this.isResting;
    }

    /**
     * Update the human server when it is serving a new customer.
     * 
     * @param cu The customer that is being served.
     * @return An updated human server with a given id that is serving a customer.
     */

    public HumanServer serve(Customer cu) {
        if (this.canServe()) {
            return new HumanServer(this.id, cu);
        }
        return this;
    }

    /**
     * Update the human server when it is serving a new customer with an updated
     * list of customers waiting in queue.
     * 
     * @param cu      The customer that is being served.
     * @param waiting The list of customer that is waiting in queue.
     * @return An updated human server with a given id and updated waiting list that
     *         is serving a customer.
     */

    public HumanServer serve(Customer cu, Queue<Customer> waiting) {
        if (this.canServe()) {
            return new HumanServer(this.id, Optional.of(cu), waiting, false, false);
        }
        return this;
    }

    /**
     * Update the human server when it is done serving a customer, with it's
     * existing waiting list of customers.
     * 
     * @return An updated human server that has finished serving.
     */

    public HumanServer doneServing() {
        return new HumanServer(this.id, this.waitingList);
    }

    /**
     * Update the human server with an updated waiting list of customers.
     * 
     * @param cu The customer to be added to the waiting queue.
     * @return An updated human server that has the customer added to the waiting
     *         queue.
     */

    public HumanServer wait(Customer cu) {
        if (this.canWait()) {
            Queue<Customer> waitingList = new LinkedList<>(this.waitingList);
            waitingList.add(cu);
            return new HumanServer(this.id, this.cust, waitingList, this.isIdle, this.isResting);
        }
        return this;
    }

    /**
     * Checks if the human server can serve a new customer. (i) Its idle (ii) Its
     * not resting.
     * 
     * @return True/False
     */

    public boolean canServe() {
        return this.isIdle && !this.isResting;
    }

    /**
     * Checks if a new customer can be added to the waiting queue. (i) The number of
     * customers in the waiting queue does not exceed the maximum queue size.
     * 
     * @return True/False
     */

    public boolean canWait() {
        if (this.getWaitingList().size() < maxQueue) {
            return true;
        }
        return false;
    }

    /**
     * Update the human server to resting when its on a break.
     * 
     * @return An updated human server that goes to rest.
     */

    public HumanServer rest() {
        return new HumanServer(this.id, this.getCustomer(), this.waitingList, this.isIdle, true);
    }

    /**
     * Update the human server to not resting when its back.
     * 
     * @return An updated human server that is no longer resting.
     */
    public HumanServer doneResting() {
        return new HumanServer(this.id, this.getCustomer(), this.waitingList, 
                this.getWaitingList().isEmpty(), false);
    }

    @Override
    public String toString() {
        return "server " + this.getID();
    }

}