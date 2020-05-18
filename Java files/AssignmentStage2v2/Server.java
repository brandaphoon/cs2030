public class Server {
    
    private final int id;
    private final static double SERVICE_TIME = 1.0;
    private final double time;
    private final Customer cust;
    private final Customer waitingCust;

    Server(int id) {
        this.id = id;
        this.completionTime = -1;
        this.cust = null;
        this.
    }

    Server(int id, double completionTime) {
        this.id = id;
        this.completionTime = completionTime;
    }

    public int getID() {
        return this.id;
    }

    public double getCompletionTime() {
        return this.completionTime;
    }

    public Server serve(Customer cu) {
        if (canServe(cu)) {
            return new Server(this.id, cu.getArrivalTime() + SERVICE_TIME);
        } else {
            return null;
        }
    }

    public boolean canServe(Customer cu) {
        if (this.completionTime < cu.getArrivalTime()) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.id + " next Service @ " + this.completionTime;
    }

}
