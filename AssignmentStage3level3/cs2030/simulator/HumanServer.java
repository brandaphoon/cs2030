package cs2030.simulator;

import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;

public class HumanServer extends Server {

    private final Queue<Customer> waitingList;

    private final boolean isIdle;

    private final boolean isResting;

    HumanServer(int id) {
        this.id = id;
        this.cust = Optional.empty();
        this.waitingList = new LinkedList<>();
        this.isIdle = true;
        this.isResting = false;
    }

    HumanServer(int id, Customer cust) {
        this.id = id;
        this.cust = Optional.of(cust);
        this.waitingList = new LinkedList<>();
        this.isIdle = false;
        this.isResting = false;
    }

    HumanServer(int id, Queue<Customer> waitingList) {
        this.id = id;
        this.cust = Optional.empty();
        this.waitingList = waitingList;
        this.isIdle = (waitingList.size() == 0);
        this.isResting = false;
    }

    HumanServer(int id, Optional<Customer> cust, Queue<Customer> waitingList, boolean isIdle,
            boolean isResting) {
        this.id = id;
        this.cust = cust;
        this.waitingList = waitingList;
        this.isIdle = isIdle;
        this.isResting = isResting; // false
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

    public HumanServer serve(Customer cu) {
        return new HumanServer(this.id, cu);
    }

    public HumanServer serve(Customer cu, Queue<Customer> waiting) {
        return new HumanServer(this.id, Optional.of(cu), waiting, false, false);
    }

    public HumanServer doneServing() {
        return new HumanServer(this.id, this.waitingList);
    }

    public HumanServer wait(Customer cu) {
        if (this.canWait()) {
            Queue<Customer> waitingList = new LinkedList<>(this.waitingList);
            waitingList.add(cu);
            return new HumanServer(this.id, this.cust, waitingList, this.isIdle, this.isResting);
        }
        return this;
    }

    // If idle and not resting, then serve
    public boolean canServe() {
        return this.isIdle && !this.isResting;
    }

    public boolean canWait() {
        if (this.getWaitingList().size() < maxQueue) {
            return true;
        }
        return false;
    }

    public HumanServer rest() {
        return new HumanServer(this.id, this.getCustomer(), this.waitingList, this.isIdle, true);
    }

    public HumanServer doneResting() {
        return new HumanServer(this.id, this.getCustomer(), this.waitingList, this.getWaitingList().isEmpty(), false);
    }

    @Override
    public String toString() {
        return "server " + this.getID();
    }

}