import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {
  
    /**
     * Collect user inputs of ArrivalTimings and prints the processing events
     * of each customer.
     */

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        int count = 0;
        
        ArrayList<Customer> customers = new ArrayList<Customer>();
        
        Server sv = new Server();

        while (sc.hasNext()) {
            if (sc.hasNextDouble()) {
                double s = sc.nextDouble();
                count++;
                Customer c = new Customer(count,s);
                customers.add(c);
            } else {
                break;
            }
        }

        sc.close();
        
        PriorityQueue<Customer> log = new PriorityQueue<Customer>(customers);
        
        System.out.println("# Adding arrivals");
        printing(log);
        
        System.out.println();
        
        while (log.size() > 0) {
            Customer cu = log.peek();
            System.out.println("# Get next event: " + cu);
            
            if (cu.getStatus() != Status.LEAVES & cu.getStatus() 
                    != Status.DONE) {
               
                ArrayList<Object> result = processEvent(cu, sv);
                if (result != null) {
                    sv = (Server) result.get(0);
                    Customer nextEvent = (Customer) result.get(1);
                    log.add(nextEvent);
                }
            }

            log.remove(cu);
            printing(log);
            System.out.println();
        }

        System.out.println("Number of customers: " + count); 
    }

    /**
     * This prints all the items in a given list in this case, it is a 
     * priorityQueue list.
     */

    public static void printing(PriorityQueue<Customer> cust) {
        if (cust.size() > 0) {
            for (Customer c: cust) {
                System.out.println(c);
            }
        }
    }

    /**
     * This is to process the events of each given customer with a server.
     * A customer with different status will undergo different events.
     */
    
    public static ArrayList<Object> processEvent(Customer cu, Server sv) {
        ArrayList<Object> list = new ArrayList<Object>(2);
       
        if (cu.getStatus() == Status.ARRIVES) {
            if (sv.serve(cu) != null) {
                list.add(sv.serve(cu)); 
                list.add(cu.statusChange(true));
            } else {
                list.add(sv);
                list.add(cu.statusChange(false));
            }

        } else if (cu.getStatus() == Status.SERVED) {
            list.add(sv);
            double serviceTime = sv.getServiceTime();
            list.add(cu.served(serviceTime));
        } 
        return list;           
    }


}
