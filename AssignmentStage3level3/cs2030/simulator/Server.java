package cs2030.simulator;

import java.util.Optional;

public abstract class Server {

    protected int id;

    protected static int maxQueue;

    protected Optional<Customer> cust;

    // protected Server(int id) {
    //     this.id = id;
    //     this.cust = Optional.empty();
    // }

    // protected Server(int id, Customer cust) {
    //     this.id = id;
    //     this.cust = Optional.of(cust);
    // }

    // protected Server(int id, Optional<Customer> cust) {
    //     this.id = id;
    //     this.cust = cust;
    // }

    public int getID() {
        return this.id;
    }

    public Optional<Customer> getCustomer() {
        return this.cust;
    }
    public abstract Server serve(Customer cu);

    public abstract Server doneServing();

    public static void setMaxQueue(int maxQueue) {
        Server.maxQueue = maxQueue;
    }

    public int getMaxQueue() {
        return Server.maxQueue;
    }

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