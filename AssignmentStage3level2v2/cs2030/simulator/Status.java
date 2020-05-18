package cs2030.simulator;
/**
 * Status enum represents Customer status.
 */

public enum Status {
    
    /**
     * Arrive status.
     */

    ARRIVES(1), 

    /**
     * Served status.
     */

    SERVED(2),

    /**
     * Leaves status.
     */

    LEAVES(3),

    /**
     * Done status.
     */

    DONE(4),

    /**
     * Waits status.
     */

    WAITS(5);

    /**
     * The value assigned to a status.
     */

    private final int statusCode;

    /**
     * Creates a new status.
     * @param statusCode the value 
     */

    Status(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
