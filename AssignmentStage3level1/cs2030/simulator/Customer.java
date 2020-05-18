package cs2030.simulator;

/**
 * Customer class represents a Customer.
 */

public class Customer implements Comparable<Customer> { 

    /**
     * The id of the customer.
     */

    private final int id;
    
    /**
     * The time of the event based on the customer's status.
     */

    private final double time;

    /**
     * The status of the customer.
     */

    private final Status status;
    
    /**
     * Creates a default customer with an id and their time 
     * with a attached arrives status and no waitingTime.
     * @param id the id of the customer
     * @param time the time that the customer arrives
     */

    public Customer(int id, double time) {
        this.id = id;
        this.time = time;
        this.status = Status.ARRIVES;
    }

    /**
     * Creates a new customer with a new status excluding WAITS, 
     * when update of the status is required.
     * @param id the id of the customer
     * @param time the time that the customer arrives or the time that a event
     based on the status is executed
     * @param status the status of the customer
     */

    public Customer(int id, double time, Status status) {
        this.id = id;
        this.time = time;
        this.status = status;
    }

    public int getID() {
        return this.id;
    }

    public double getTime() {
        return this.time;
    }

    public Status getStatus() {
        return this.status;
    }


    /**
     * Creates a new customer when the status of the customer
     * is changed (i) when the customer can be served, the status 
     * will be updated to SERVED (ii) else the customer will leave.
     * @param serve true/false if the customer can be served
     * @return A new updated customer
     */

    /*public Customer statusChange(boolean serve) {
        if (serve) {
            return new Customer(this.id, this.time, Status.SERVED);
        }
        return new Customer(this.id, this.time, Status.LEAVES);
    }

    public Customer waiting() {
        return new Customer(this.id, this.time, Status.WAITS);
    }*/
    
    public Customer statusChange(Status status) {
        return new Customer(this.id, this.time, status);
    }

    /**
     * Updates the status of the customer after being served and
     * the time that their service is completed.
     * @param serviceTime the service time of the customer completed service
     * @return A new updated customer
     */

    public Customer served(double serviceTime) {
        return new Customer(this.id, this.time + serviceTime, 
                Status.DONE);
    }

    @Override
    public int compareTo(Customer cu) {
        if (cu.getTime() <  this.getTime()) {
            return 1;    
        } else if (cu.getTime() > this.getTime()) {
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Customer cu = (Customer) obj;
        return this.id == cu.getID();
    }        

    @Override
    public String toString() {
        return String.format("%.3f", this.getTime()) + " " + 
            this.getID() + " " + this.getStatus().toString().toLowerCase();
    }
}
