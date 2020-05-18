public class Customer implements Comparable<Customer> { 

    private final int id;
    private final double arrivalTime;
    private final Status status;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = Status.ARRIVES;
    }

    public Customer(int id, double arrivalTime, Status status) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = status;
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

    public Customer statusChange(String status) {
        if (status == "wait") {
            return new Customer(this.id, this.arrivalTime, Status.WAITS);
        } else if (status == "serve") {
            return new Customer(this.id, this.arrivalTime, Status.SERVED);
        }
        return new Customer(this.id, this.arrivalTime, Status.LEAVES);
    }
   
    // Creating a new start time for waiting cust
    public Customer newStartTime(Server sv) {
        return new Customer(this.id, sv.getCompletionTime(), Status.SERVED);
    }

    public Customer served(Server sv) {
        return new Customer(this.id, sv.getCompletionTime(), 
                Status.DONE);
    }

    @Override
    public int compareTo(Customer cu) {
        if (cu.getArrivalTime() < this.getArrivalTime()) {
            return 1;
        } else if (cu.getArrivalTime() > this.getArrivalTime()) {
            return -1;
        } else {
            if (cu.getStatus().getStatusCode() > 
                    this.getStatus().getStatusCode()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.getArrivalTime()) + " " + 
            this.getID() + " " + this.getStatus().toString().toLowerCase();
    }
}
