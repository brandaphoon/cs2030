public class Server {
    
    private final static double SERVICE_TIME = 1.0;
    private final int id;
    private final double time;
    private final Customer cust;
  

    Server(int id) {
        this.id = id;
        this.time = -1;
        this.cust = null;
    }

    Server(int id, double time, Customer cust) {
        this.id = id;
        this.time = time;
        this.cust = cust;
    }

    public int getId() {
        return this.id;
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.cust;
    }

    public Server setCustomer(Customer cust) {
        return new Server(this.id, cust.getArr() + SERVICE_TIME, cust);
    }

    public Server serve(Customer cust) {
        if (cust == null) {
            return this;
        }

        if (this.canServe(cust)) {
            return setCustomer(cust);
        } else {
            return null;
        }
    }

    public boolean canServe(Customer cust) {
        if (cust == null) {
            return true;
        }

        if (cust.getArr() >= this.time) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.cust == null) {
            return "Server " + this.id;
        } else {
            return "Server " + this.id + " serving " + this.cust;
        }
    }
}
