public class Server {
    
    private final Customer cust;
    private static final double serviceTime = 1.0;
    private final double completionTime;

    /**
     * Creates a new Server without a customer and a -1 completionTime.
     * This represents a new Server that has not been assigned a customer.
     * Thus, this will not require an input.
     */
    
    public Server() {
        this.cust = null;
        this.completionTime = -1;
    }
    
    /**
     * Creates a new Server serving a customer, including the completionTime 
     * of the entire service.
     * The input will be the Customer's class object.
     */
    
    public Server(Customer cust) {
        this.cust = cust;
        this.completionTime = this.serviceTime + cust.getArrivalTime();
    }
    
    public Customer getCust() {
        return this.cust;
    }

    public double getCompletionTime() {
        return this.completionTime;
    }

    public double getServiceTime() {
        return this.serviceTime;
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

    @Override
    public String toString() {
        return "Customer served; next service @ " + 
            String.format("%.3f",this.completionTime);
    }
           
}
