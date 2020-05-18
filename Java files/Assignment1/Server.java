public class Server {
    
    private final Customer cust;
    private final static double serviceTime = 1.0;
    private final double completionTime;

    public Server() {
        this.cust = null;
        this.completionTime = -1;
    };

    public Server(Customer cust) {
        this.cust = cust;
        this.completionTime = this.serviceTime + cust.getArrivalTime();
    }

    public Server serve(Customer cust) {
        if (this.canServe(cust)) {
            return new Server(cust);
        } else {
            return null;
        }
    }

    public boolean canServe(Customer cust) {
        if (cust.getArrivalTime() >= this.completionTime) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Customer served; next service @ " + 
            String.format("%.3f",this.completionTime);
    }
           
}
