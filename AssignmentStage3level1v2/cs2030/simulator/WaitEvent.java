package cs2030.simulator;

public class WaitEvent extends CustomerEvent {

    private Server sv;

    WaitEvent(double time, Customer cust, Server sv) {
        this.time = time;
        this.cust = cust;
        this.sv = sv.wait(cust);
    }

    public Server getServer() {
        return sv;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust.getID() + " waits to be served by server " + this.sv.getID();
    }
}