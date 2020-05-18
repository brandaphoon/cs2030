package cs2030.simulator;

public class Stats {

    private final int count;

    private final int served;

    private final double waitingTime;

    public Stats(int count, int served, double waitingTime) {
        this.count = count;
        this.served = served;
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        int notServed = count - served;
        String avgWaiting =  String.format("%.3f", ( (waitingTime != 0) ? (waitingTime / served) : 0));

        return ("[" + avgWaiting + " " + served + " " + notServed +"]");
    }
}