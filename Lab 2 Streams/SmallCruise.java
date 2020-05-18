public class SmallCruise extends Cruise {
    
    private final static int reqTime = 30;
    private final static int noOfLoader = 1;
    
    public SmallCruise(String id, int arrival) {
        super(id, arrival, noOfLoader, reqTime);
 
    }
    
}