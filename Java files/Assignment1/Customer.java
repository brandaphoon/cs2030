public class Customer { 

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

    public Customer statusChange(boolean serve) {
        if (serve) {
            return new Customer(this.id, this.arrivalTime, Status.SERVED);
        }
        return new Customer(this.id, this.arrivalTime, Status.LEAVES);
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.getArrivalTime()) + " " + 
            this.getID() + " " + this.status.asLowerCase();
    }
}
