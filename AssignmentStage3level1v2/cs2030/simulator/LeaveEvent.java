package cs2030.simulator;

public class LeaveEvent extends CustomerEvent {

    LeaveEvent(double time, Customer cust) {
        this.time = time;
        this.cust = cust;
    }

    public Customer getCustomer() {
        return this.cust;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust.getID() + " leaves";
    }

}