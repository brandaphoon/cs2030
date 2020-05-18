public class Customer implements Comparable<Customer> { 

    private final int id;
    private final double arrivalTime;
    private final Status status;
    private final double waitingTime;

    private static final double serviceTime = 1.0;

    /**
     * Creates a customer with an id and their arrivalTime while attaching
     * an arrive status with them and 0 waitingTime.
     */

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = Status.ARRIVES;
        this.waitingTime = 0;
    }

    /**
     * Creates a new customer with a new status that is not waiting, 
     * when update of the status is required.
     */

    public Customer(int id, double arrivalTime, Status status) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.waitingTime = 0;
    }

    /**
     * Creates a new customer with a new status specifically for customers
     * who are waiting.
     * Hence, the input includes the waitingTime.
     */

    public Customer(int id, double arrivalTime, Status status, double time) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.waitingTime = time;
    }

    public int getID() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public Status getStatus() {
        return this.status;
    }
    
    public double getWaitingTime() {
        return this.waitingTime;
    }

    /**
     * This method is to create a new customer when the status of the customer
     * is changed which is when the customer can be served, the status 
     * will be updated to SERVES else it the customer will leave.
     * 
     */

    public Customer statusChange(boolean serve) {
        if (serve) {
            return new Customer(this.id, this.arrivalTime, Status.SERVED);
        }
        return new Customer(this.id, this.arrivalTime, Status.LEAVES);
    }

    public Customer waiting(double time) {
        return new Customer(this.id, this.arrivalTime, Status.WAITS, time);
    }
    
    /**
     * This is to update the status of the customer after being served and
     * the time that their service is completed.
     */

    public Customer served(double serviceTime) {
        return new Customer(this.id, this.arrivalTime + serviceTime, 
                Status.DONE);
    }

    @Override
    public int compareTo(Customer cu) {
        if (cu.getArrivalTime() <  this.getArrivalTime()) {
            return 1;    
        } else if (cu.getArrivalTime() > this.getArrivalTime()) {
            return -1;    
        } else if (cu.getID() < this.getID()) {
            return 1;
        } else if (cu.getID() > this.getID()) {
            return -1;
        } else if (cu.getStatus().getStatusCode() 
                < this.getStatus().getStatusCode()) {
            return 1;
        } else {
            return -1;
        }
    } 

    @Override
    public String toString() {
        return String.format("%.3f", this.getArrivalTime()) + " " + 
            this.getID() + " " + this.getStatus().toString().toLowerCase();
    }
}
