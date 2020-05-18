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
        
        PriorityQueue<Customer> customers = new PriorityQueue<Customer>();
        
        PriorityQueue<Server> servers = new PriorityQueue<Server>();
        
        int numServers = sc.nextInt();

        servers = createServers(numServers);

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
        
        int served = 0;
        double totalWaitingTime = 0;

        PriorityQueue<Customer> finalLog = new PriorityQueue<Customer>();
        
        /* while (customers.size() > 0) {

            Customer cu = customers.peek();
            
            // Add total waiting time
            if (cu.getStatus() == Status.WAITS) {
                totalWaitingTime += cu.getWaitingTime();
            }

            if (cu.getStatus() == Status.DONE & sv.getWaiting() != null) {
                ArrayList<Object> waitingResult = processWaiting(sv);
                sv = (Server) waitingResult.get(0);
                Customer afterWaiting = (Customer) waitingResult.get(1);
                
                // Add customers who are served
                served++;
                customers.add(afterWaiting);
            }

            if (cu.getStatus() != Status.LEAVES & cu.getStatus() 
                    != Status.DONE) {
               
                ArrayList<Object> result = processEvent(cu, sv);
                
                if (result != null & result.size() == 2) {
                    sv = (Server) result.get(0);
                    Customer nextEvent = (Customer) result.get(1);
                    
                    if (nextEvent.getStatus() == Status.SERVED) {
                        served++;
                    }
                    
                    customers.add(nextEvent);
                }
            }

            finalLog.add(cu);
            customers.remove(cu);
        } */

        printing(customers);
        printing(servers);

        //printing(finalLog);

        //printStats(totalWaitingTime, count, served);
    }

    /**
     * This prints all the items in a given list in this case, it is a 
     * priorityQueue list.
     */

    public static <T> void printing(PriorityQueue<T> values) {
        if (values.size() > 0) {
            for (T v: values) {
                System.out.println(v);
            }
        }
    }
    
    public static PriorityQueue<Server> createServers(int num) {
        PriorityQueue<Server> servers = new PriorityQueue<Server>();
        
        for(int i = 1; i < num + 1; i++) {
            servers.add(new Server(i));
        }
        
        return servers;
    
    }

    public static ArrayList<ArrayList<Server>> serveCustomer(ArrayList<Server>
            servers, ArrayList<Server> events, Customer cu) {
        ArrayList<ArrayList<Server>> list = new ArrayList<ArrayList<Server>>(2);

        boolean served = false;

        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).canServe(cu)) {
                served = true;
                events.add(servers.get(i).serve(cu));
                servers.set(i, servers.get(i).serve(cu));
                break;
            }
        }

        if (!served) {
            for (int i = 0; i < servers.size(); i++) {
                if (servers.get(i).canWait(cu)) {
                    served = true;
                    events.add(servers.get(i).wait(cu));
                    servers.set(i, servers.get(i).serve(cu));
                    break;
                }
            }
        }

        list.add(
    }


    /**
     * Calculates the statistics required and prints it out in desired format.
     */
    
   /* public static void printStats(double waitingTime, int count, int served) {
        
        int notServed = count - served;
        String avgWaiting = String.format("%.3f", (waitingTime / served));

        System.out.println("[" + avgWaiting + " " + served + " " + notServed +
                "]");
    } */

    /**
     * This is to process the events of each given customer with a server.
     * A customer with different status will undergo different events.
     */
    
    /* public static ArrayList<Object> processEvent(Customer cu, Server sv) {
        ArrayList<Object> list = new ArrayList<Object>(2);
       
        if (cu.getStatus() == Status.ARRIVES) {
            if (sv.serve(cu) != null) {
                list.add(sv.serve(cu)); 
                list.add(cu.statusChange(true));
            } else {
                if (sv.wait(cu) != null) {
                    list.add(sv.wait(cu));
                    double waitingTime = sv.getCompletionTime() -
                        cu.getArrivalTime();
                    list.add(cu.waiting(waitingTime));
                } else if (sv.wait(cu) == null) {
                    list.add(sv);
                    list.add(cu.statusChange(false));
                }
            }
        } else if (cu.getStatus() == Status.SERVED) {
            list.add(sv);
            double serviceTime = sv.getServiceTime();
            list.add(cu.served(serviceTime));
        } 
        return list;           
    } */

   
    /**
     * This is to process the waiting events of a given customer with a server.
     */

    /* public static ArrayList<Object> processWaiting(Server sv) {
        ArrayList<Object> list = new ArrayList<Object>(2);

        Customer cu = sv.getWaiting();
        double newStartTime = sv.getCompletionTime();
        cu = new Customer(cu.getID(), newStartTime);
        Server newSv = new Server();

        list.add(newSv.serve(cu));
        list.add(cu.statusChange(true));
        
        return list;
    } */

}

