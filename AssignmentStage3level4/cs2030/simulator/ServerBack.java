package cs2030.simulator;

/**
 * ServerBack class represents an Event when the server is back from resting.
 */

public class ServerBack extends Event {

    /**
     * The served that is back from resting.
     */

    private final Server sv;

    /**
     * Creates a new ServerBack event with the time the server is back and the server.
     * @param time The time of when the server is back from resting.
     * @param sv The server that is back from resting.
     */

    ServerBack(double time, Server sv) {
        this.time = time;
        this.sv = sv;
    }

    public Server getServer() {
        return this.sv;
    }

    @Override
    public String toString() {
        return this.getTime() + " " + this.sv.getID() + " back";
    }

}