package cs2030.simulator;

public abstract class ServerEvent extends Event {
    
    protected Server sv;

    public Server getServer() {
        return sv;
    }
    
}