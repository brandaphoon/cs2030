package cs2030.simulator;

public class WaitEvent extends CustomerEvent {

    private Server sv;

    WaitEvent(double time, Customer cust, Server sv) {
        this.time = time;
        this.cust = cust;
        if (sv instanceof HumanServer) {
            this.sv = ((HumanServer) sv).wait(cust);
        } else {
            this.sv = sv;
            SelfCheck.getWaitingList().add(cust);
        }        
        
    }

    public Server getServer() {
        return sv;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust.getID() + " waits to be served by " + sv.toString();
    }
}