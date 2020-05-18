package cs2030.simulator;

public class ServerBack extends ServerEvent {

    ServerBack(double time, Server sv) {
        this.time = time;
        this.sv = sv;
    }

    public boolean canServe(ArriveEvent ae) {
        if (this.time < ae.getTime()) {
            return true;
        }
        return false;
    }

    public boolean canWait() {
        return sv.canWait();
    }

    @Override
    public String toString() {
        return this.getTime() + " " + this.sv.getID() + " back";
    }
    
}