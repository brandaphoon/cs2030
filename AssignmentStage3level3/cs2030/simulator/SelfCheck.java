package cs2030.simulator;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class SelfCheck extends Server {

    private static Queue<Customer> selfCheckQueue = new LinkedList<>();

    SelfCheck(int id) {
        this.id = id;
        this.cust = Optional.empty();
    }

    SelfCheck(int id, Customer cust) {
        this.id = id;
        this.cust = Optional.of(cust);
    }

    public static boolean canWait() {
        return selfCheckQueue.size() < maxQueue;
    }

    public static Queue<Customer> getWaitingList() {
        return selfCheckQueue;
    }

    public SelfCheck doneServing() {
        return new SelfCheck(this.id);
    }

    public SelfCheck serve(Customer cu) {
        return new SelfCheck(this.id, cu);
    }

    @Override
    public String toString() {
        return "self-check " + this.getID();
    }
    
}