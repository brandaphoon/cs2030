package cs2030.simulator;

import java.util.Queue;

public class ServedEvent extends CustomerEvent {

    private double doneTime;

    private Server sv;

    ServedEvent(double time, double serviceTime, Customer cust, Server sv) {
        this.time = time;
        this.doneTime = serviceTime + time;
        this.cust = cust;
        this.sv = sv.serve(cust);
    }

    ServedEvent(double time, double serviceTime, Customer cust, Server sv, Queue<Customer> waiting) {
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
        return String.format("%.3f", this.time) + " " + this.cust.getID() + " served by " + this.sv.toString();
    }

}