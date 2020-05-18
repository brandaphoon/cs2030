package cs2030.simulator;

public class DoneEvent extends CustomerEvent {

    private Server sv;

    DoneEvent(double time, Customer cust, Server sv) {
        this.time = time;
        this.cust = cust;
        this.sv = sv;
    }

    public Server getServer() {
        return sv;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust.getID() + " done serving by server " + this.sv.getID();
    }

}