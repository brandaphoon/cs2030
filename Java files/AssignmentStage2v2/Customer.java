public class Customer implements Comparable<Customer> {
    
    private final int id; 
    private final double arrivalTime;
    private final Status status;
    
    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = Status.ARRIVES;
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

    @Override
    public int compareTo(Customer cu) {
        if (cu.getID() <  this.getID()) {
            return 1;
        } else {
            return -1;
        }
    }

}

