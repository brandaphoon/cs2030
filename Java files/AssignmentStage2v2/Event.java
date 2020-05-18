public class Event implements Comparable<Event> {
    private final Customer cu;
    private final Server sv;
    private final double atTime;

    Event(Customer cu, Server sv, double atTime) {
        this.cu = cu;
        this.sv = sv;
        this.atTime = atTime;
    }

    Event(Customer cu) {
        this.cu = cu;
        this.sv = null;
        this.atTime = cu.getArrivalTime();
    }
    
    public Customer getCustomer() {
        return this.cu;
    }

    public Server getServer() {
        return this.sv;
    }

    public double getAtTime() {
        return this.atTime;
    }

    @Override
    public int compareTo(Event ev) {
        if (ev.getCustomer().getStatus

    @Override
    public String toString() {
        String str = String.format("%.3f", this.getAtTime()) + " " 
            + this.cu.getID();    
        
        switch(this.cu.getStatus()) {
            case LEAVES:
                return str + " leaves";
            case SERVED:
                return str + " served by " + this.sv.getID();
            case WAITS:
                return str + " waits to be served by " + this.sv.getID();
            case DONE:
                return str + " done serving by " + this.sv.getID();
            default:
                return str + " arrives";
        }
    }
}
