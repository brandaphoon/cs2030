
import cs2030.simulator.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Represents the logic of a Discrete Event Simulator with Multiple Servers.
 */

public class Main {
  
    /**
     * Collect user inputs of ArrivalTimings and prints the processing events
     * of each customer.
     */

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        //int seed = sc.nextInt();

        int numOfSv = sc.nextInt();

        int maxNo = sc.nextInt();

        //int noCust = sc.nextInt();

        //double arrivalRate = sc.nextDouble();

       // double serviceRate = sc.nextDouble();

        

        ArrayList<Server> servers = addServers(numOfSv, maxNo);

        int count = 0;
        
        PriorityQueue<Customer> customers = new PriorityQueue<Customer>();

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
        
        
        PriorityQueue<Event> finalLog = new PriorityQueue<Event>();

        while (customers.size() > 0) {

            Customer cu = customers.peek();
            
            // Add total waiting time
            if (cu.getStatus() == Status.WAITS) {
                totalWaitingTime += cu.getWaitingTime();
            }

            Server sv = findServer(cu,servers);

            if (cu.getStatus() == Status.DONE && sv.getWaiting() != null) {
                
                
                ArrayList<Object> waitingResult = processWaiting(sv);
                Server newSv = (Server) waitingResult.get(0);
                servers.set(servers.indexOf(sv), newSv);
                Customer afterWaiting = (Customer) waitingResult.get(1);
                
                // Add customers who are served
                served++;
                customers.add(afterWaiting);
      
            }

            if (cu.getStatus() != Status.LEAVES & cu.getStatus() 
                    != Status.DONE) {

                ArrayList<Object> result = processEvent(cu, sv);
                
                if (result != null & result.size() == 2) {
                    
                    Server newSv = (Server) result.get(0);
                    servers.set(servers.indexOf(sv), newSv);
                    Customer nextEvent = (Customer) result.get(1);
                    
                    if (nextEvent.getStatus() == Status.SERVED) {
                        served++;
                    }
              
                    customers.add(nextEvent);
                }
            }
                        
            finalLog.add(new Event(cu, sv));
            customers.remove(cu);
      
        }

        printing(finalLog);

        printStats(totalWaitingTime, count, served);
        
    }

    /**
     * This is a generic print method that prints all the items 
     * in a given priority list.. 
     */

    public static <T> void printing(PriorityQueue<T> list) {
        if (list.size() > 0) {
            for (T t: list) {
                System.out.println(t);
            }
        }
    }

    /**
     * Calculates the statistics required and prints it out in desired format.
     */
    
    public static void printStats(double waitingTime, int count, int served) {
        
        int notServed = count - served;
        String avgWaiting = String.format("%.3f", (waitingTime / served));

        System.out.println("[" + avgWaiting + " " + served + " " + notServed +
                "]");
    }

    /**
     * Create and add n new servers to a new array list. 
     * @return An array list of servers.
     */

    public static ArrayList<Server> addServers(int n, int maxNo) {
        ArrayList<Server> servers = new ArrayList<Server>();

        for (int i = 1; i < n + 1; i++) {
            servers.add(new Server(i, maxNo));
        }
        
        return servers;
    }

    /**
     * Find a server that would be able to serve/wait the given customer.
     * @return The server.
     */ 
    
    public static Server findServer(Customer cu, ArrayList<Server> servers) {

        for (Server s: servers) {
            if (cu.getStatus() == Status.DONE || cu.getStatus() == 
                    Status.SERVED) {
                if (s.getCustomer().equals(cu)) {
                    return s;
                }
            } else if (cu.getStatus() == Status.WAITS) {
                if (s.getWaiting().get(0).equals(cu)) {
                    return s;
                }
            }
        }
             
        for (Server s: servers) {
            if (s.canServe(cu)) {
                return s;
            }
        }
       
        for (Server s: servers) {
            if (s.canWait()) {
                return s;
            }
        }
        
        return servers.get(0);
    }



    /**
     * This is to process the events of each given customer with a server.
     * A customer with different status will undergo different events.
     * @return An array list that contains an updated server and customer.
     */ 

    public static ArrayList<Object> processEvent(Customer cu, Server sv) {
        ArrayList<Object> list = new ArrayList<Object>(2);
       
        if (cu.getStatus() == Status.ARRIVES) {
            if (sv.serve(cu) != null) {
                list.add(sv.serve(cu)); 
                list.add(cu.statusChange(true));
            } else {
                if (sv.wait(cu) != null) {
                    list.add(sv.wait(cu));
                    double waitingTime = sv.getCompletionTime() -
                        cu.getTime();
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
    }

    /**
     * Process the waiting events of a given customer with a server.
     * @return An array list that contains an updated server and customer.
     */

    public static ArrayList<Object> processWaiting(Server sv) {
        ArrayList<Object> list = new ArrayList<Object>(2);

        ArrayList<Customer> waitingList = new ArrayList<>(sv.getWaiting());
        Customer cu = waitingList.get(0);
        waitingList.remove(0);
        double newStartTime = sv.getCompletionTime();
        cu = new Customer(cu.getID(), newStartTime);
        Server newSv = new Server(sv.getID(), cu, waitingList, sv.getMaxCustomers());
        //Server newSv = new Server(sv.getID(), sv.getMaxCustomers());

        list.add(newSv.serve(cu));
        list.add(cu.statusChange(true));
        
        return list;
    }

}
