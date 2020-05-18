public class BigCruise extends Cruise {

   private final static int perLength = 40;
   private final static int perPassenger = 50;

   BigCruise(String id, int arrival, int length, int noOfPassengers){
        super(id, arrival,(int) Math.ceil((double) length / perLength), 
                (int) Math.ceil((double) noOfPassengers / perPassenger));
   }

}
