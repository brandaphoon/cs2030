public class Server {
    
    private final Customer cust;
    private final Customer waiting;
    private final static double serviceTime = 1.0;
    private final double completionTime;

    public Server() {
        this.cust = null;
        this.waiting = null;
        this.completionTime = -1;
    };

    public Server(Customer cust) {
        this.cust = cust;
        this.waiting = null;
        this.completionTime = this.serviceTime + cust.getArrivalTime();
    }

    // This is to add the waiting customer
    public Server(Customer c1, Customer c2) {
        this.cust = c1;
        this.waiting = c2;
        this.completionTime = this.serviceTime + c1.getArrivalTime();
    }

    public Customer getCustomer() {
        return this.cust;
    }

    public Customer getWaiting() {
        return this.waiting;
    }

    public double getCompletionTime() {
        return this.completionTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public Server serve(Customer cust) {
        if (this.canServe(cust)) {
            return new Server(cust);
        } else {
            return null;
        }
    }

    public Server wait(Customer cu) {
        if (this.canWait()) {
            return new Server(this.getCustomer(), cu);
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

    public boolean canWait() {
        if (this.getWaiting() == null) {
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
