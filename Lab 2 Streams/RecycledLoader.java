public class RecycledLoader extends Loader {

    RecycledLoader(int id) {
        super(id);
    }

    RecycledLoader(int id, int time, Cruise cruise) {
        super(id, time, cruise);
    }

    @Override
    public Loader setCruise(Cruise cruise) {
        return new RecycledLoader(this.id, cruise.getServiceCompletionTime() 
                + 60, cruise);
    }

    @Override
    public String toString() {
        if (this.cruise == null) {
            return "Loader " + this.id + " (recycled)";
        } else {
            return "Loader " + this.id + " (recycled) serving " + this.cruise;
        }
    }
}

