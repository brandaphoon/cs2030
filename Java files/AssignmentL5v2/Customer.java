public class Customer implements Comparable<Customer> { 

    private final int id;
    private final double arrivalTime;
    private final Status status;
    private final double waitingTime;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = Status.ARRIVES;
        this.waitingTime = 0;
    }

    public Customer(int id, double arrivalTime, Status status) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.waitingTime = 0;
    }

    public Customer(int id, double arrivalTime, Status status, double wait) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.waitingTime = wait;
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

    public Customer switchStatus(String status, double time) {
        if (status == "serve") {
            return new Customer(this.id, time, Status.SERVED);
        } else if (status == "wait") {
            return new Customer(this.id, this.arrivalTime, Status.WAITS, time);
        } else {
            return new Customer(this.id, this.arrivalTime, Status.LEAVES);
        }
    }

    public Customer served(double serviceTime) {
        return new Customer(this.id, this.arrivalTime + serviceTime, 
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
