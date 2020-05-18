public class Server {
    
    private final Customer cust;
    private final Customer waiting;
    private static final double serviceTime = 1.0;
    private final double completionTime;

    /**
     * Creates a new Server without a customer or a waiting customer and a 
     * -1 completionTime.
     * This represents a new Server that has not been assigned a customer or
     * a waiting customer. Thus, this will not require an input.
     */
    
    public Server() {
        this.cust = null;
        this.waiting = null;
        this.completionTime = -1;
    }
    
    /**
     * Creates a new Server serving a customer, including the completionTime 
     * of the entire service.
     * The input will be the Customer's class object.
     */
    
    public Server(Customer cust) {
        this.cust = cust;
        this.waiting = null;
        this.completionTime = this.serviceTime + cust.getArrivalTime();
    }

    /**
     * Creates a new Server with a customer waiting.
     */

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
    
    /**
     * This is a method to create a new Server serving a customer when
     * the server is available or fit the preconditions to serve the input
     * customer.
     */

    public Server serve(Customer cust) {
        if (this.canServe(cust)) {
            return new Server(cust);
        } else {
            return null;
        }
    }


    /**
     * This is a method to create a new updated server with a waiting customer
     * when all the preconditions are met.
     */

    public Server wait(Customer cu) {
        if (this.canWait()) {
            return new Server(this.getCustomer(), cu);
        } else {
            return null;
        }
    }

    /**
     * This is a method to check whether the input customer can be served by
     * the server based on the customer's arrivalTime and the server's 
     * completionTime.
     */
    
    public boolean canServe(Customer cust) {
        if (cust.getArrivalTime() >= this.completionTime) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is a method to check whether a customer can be added to wait for
     * the server.
     */

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
