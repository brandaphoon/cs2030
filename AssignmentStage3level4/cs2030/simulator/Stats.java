package cs2030.simulator;

/**
 * Stats class represents that statistcs that is needed to be generated for the
 * event simulation.
 */

public class Stats {

    /**
     * The number of customers that arrived.
     */

    private final int count;

    /**
     * The number of customer that were served.
     */

    private final int served;

    /**
     * The total amount of waiting time for all the customers that are waiting.
     */

    private final double waitingTime;

    /**
     * Creates a new Stats with all the required inputs.
     * 
     * @param count       The total number of customers that arrived.
     * @param served      The number that has been served.
     * @param waitingTime The total waiting time.
     */

    public Stats(int count, int served, double waitingTime) {
        this.count = count;
        this.served = served;
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        int notServed = count - served;
        String avgWaiting = String.format("%.3f", 
                ((waitingTime != 0) ? (waitingTime / served) : 0));

        return ("[" + avgWaiting + " " + served + " " + notServed + "]");
    }
}