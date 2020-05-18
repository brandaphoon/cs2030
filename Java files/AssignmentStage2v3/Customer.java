public class Customer {

    private final int id;
    private final double arrival;
    private final Status status;
    
    public Customer(int id, double arrival) {
        this.id = id;
        this.arrival = arrival;
        this.status = Status.ARRIVES;
    } 
   
    public int getId() {
        return this.id;
    }

    public double getArr() {
        return this.arrival;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.id + " " + String.format("%.3f", this.arrival);
    }
}
