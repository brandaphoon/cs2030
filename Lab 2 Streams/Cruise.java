import java.util.stream.*;

public class Cruise {

    private final String id;
    private final int arrival;
    private final int noOfLoaders;
    private final int serviceTime;
    
    protected Cruise(String id, int arrival, int noOfLoaders, int serviceTime) {
        this.id = id;
        this.arrival = arrival;
        this.noOfLoaders = noOfLoaders;
        this.serviceTime = serviceTime;
    } 
   
    public String getId() {
        return this.id;
    }

    public int getArr() {
        return this.arrival;
    }

    public int getNumOfLoadersRequired() {
        return this.noOfLoaders;
    }

    public int getServiceTime() {
        return this.serviceTime;
    }

    public int getArrivalTime() {
        /*int hour = this.arrival / 100;
        int mins = this.arrival % 100;
        int time = (hour * 60) + mins;
        return time;*/

        return Stream.of(this.arrival).map(x -> ((x/100) * 60) + (x % 100)).findFirst().get();
    }

    public int getServiceCompletionTime() {
        int completion = this.getArrivalTime() + this.getServiceTime();
        return completion;
    }

    @Override
    public String toString() {
        return String.format(this.id + "@%04d", this.arrival);
    }
}