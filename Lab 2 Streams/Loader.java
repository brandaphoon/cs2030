public class Loader {
    
    protected final int id;
    protected final int time;
    protected final Cruise cruise;

    protected Loader(int id) {
        this.id = id;
        this.time = -1;
        this.cruise = null;
    }

    protected Loader(int id, int time, Cruise cruise) {
        this.id = id;
        this.time = time;
        this.cruise = cruise;
    }

    public int getId() {
        return this.id;
    }

    public int getTime() {
        return this.time;
    }

    public Cruise getCruise() {
        return this.cruise;
    }

    public Loader setCruise(Cruise cruise) {
        return new Loader(this.id, cruise.getServiceCompletionTime(), cruise);
    }

    public Loader serve(Cruise cruise) {
        if (cruise == null) {
            return this;
        }

        if (this.canServe(cruise)) {
            return setCruise(cruise);
        } else {
            return null;
        }
    }

    public boolean canServe(Cruise cruise) {
        if (cruise == null) {
            return true;
        }

        if (cruise.getArrivalTime() >= this.time) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.cruise == null) {
            return "Loader " + this.id;
        } else {
            return "Loader " + this.id + " serving " + this.cruise;
        }
    }
}