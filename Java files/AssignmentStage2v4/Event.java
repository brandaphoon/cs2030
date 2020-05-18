/**
 * Event class represents an Event object that models the event 
 * between a Customer and a Server.
 */

public class Event implements Comparable<Event> {
    
    /**
     * The customer that is undergoing the event.
     */

    private final Customer cu;
    
    /**
     * The server that is associated with the event.
     */
    
    private final Server sv; 
    
    /**
     * Creates a event with a customer and server.
     * @param cu the customer undergoing an event
     * @param sv the server that is associated with the event
     */

    Event(Customer cu, Server sv) {
        this.cu = cu;
        this.sv = sv;
    }

    public Customer getCust() {
        return this.cu;
    }

    public Server getSv() {
        return this.sv;
    }

    @Override
    public int compareTo(Event ev) {
        return this.getCust().compareTo(ev.getCust());
    }
            

    @Override
    public String toString() {
        String out = this.cu.toString();
        switch (cu.getStatus()) {
            case SERVED:
                return out += " by " + this.sv.getID();
            case WAITS:
                return out += " to be served by " + this.sv.getID();
            case DONE:
                return out += " serving by " + this.sv.getID();
            default:
                return out;
        }
    }
}

