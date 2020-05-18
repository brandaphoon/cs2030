package cs2030.simulator;

public class ArriveEvent extends CustomerEvent {

    ArriveEvent(double time, Customer cust) {
        this.time = time;
        this.cust = cust;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " " + this.cust.getID() + " arrives";
    }

}