package cs2030.simulator;

import java.util.Optional;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server {

    private final int id;

    private final Optional<Customer> cust;

    private final int maxQueue;

    private final Queue<Customer> waitingList;

    private final boolean isIdle;

    private final boolean isResting;

    Server(int id, int maxQueue) {
        this.id = id;
        this.cust = Optional.empty();
        this.maxQueue = maxQueue;
        this.waitingList = new LinkedList<>();
        this.isIdle = true;
        this.isResting = false;
    }

    Server(int id, Customer cust, int maxQueue) {
        this.id = id;
        this.cust = Optional.of(cust);
        this.maxQueue = maxQueue;
        this.waitingList = new LinkedList<>();
        this.isIdle = false;
        this.isResting = false;
    }

    Server(int id, int maxQueue, Queue<Customer> waitingList) {
        this.id = id;
        this.cust = Optional.empty();
        this.maxQueue = maxQueue;
        this.waitingList = waitingList;
        this.isIdle = (waitingList.size() == 0);
        this.isResting = false;
    }

    Server(int id, Optional<Customer> cust, int maxQueue, Queue<Customer> waitingList, boolean isIdle,
            boolean isResting) {
        this.id = id;
        this.cust = cust;
        this.maxQueue = maxQueue;
        this.waitingList = waitingList;
        this.isIdle = isIdle;
        this.isResting = isResting; // false
    }

    public int getID() {
        return this.id;
    }

    public int getMaxQueue() {
        return this.maxQueue;
    }

    public Optional<Customer> getCustomer() {
        return this.cust;
    }

    public Queue<Customer> getWaitingList() {
        return this.waitingList;
    }

    public boolean isIdle() {
        return this.isIdle;
    }

    public boolean isResting() {
        return this.isResting;
    }

    public Server serve(Customer cu) {
        return new Server(this.id, cu, this.maxQueue);
    }

    public Server serve(Customer cu, Queue<Customer> waiting) {
        return new Server(this.id, Optional.of(cu), this.maxQueue, waiting, false, false);
    }

    public Server doneServing() {
        return new Server(this.id, this.maxQueue, this.waitingList);
    }

    public Server wait(Customer cu) {
        if (this.canWait()) {
            Queue<Customer> waitingList = new LinkedList<>(this.waitingList);
            waitingList.add(cu);
            return new Server(this.id, this.cust, this.maxQueue, waitingList, this.isIdle, this.isResting);
        }
        return this;
    }

    // If idle and not resting, then serve
    public boolean canServe() {
        return this.isIdle && !this.isResting;
    }

    public boolean canWait() {
        if (this.getWaitingList().size() < this.maxQueue) {
            return true;
        }
        return false;
    }

    public Server rest() {
        return new Server(this.id, this.getCustomer(), this.maxQueue, this.waitingList, this.isIdle, true);
    }

    public Server doneResting() {
        return new Server(this.id, this.getCustomer(), this.maxQueue, this.waitingList, this.getWaitingList().isEmpty(), false);
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