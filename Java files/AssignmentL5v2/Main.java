import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int count = 0;

        ArrayList<Customer> customers = new ArrayList<Customer>();

        Server sv = new Server();

        while (sc.hasNext())
        {
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

        double waitingTime = 0;

        ArrayList<Customer> log = customers;

        ArrayList<Customer> finalLog = new ArrayList<Customer>();

        while (log.size() > 0) {
            Customer cu = log.get(0);

            // Add waiting time
            if (cu.getStatus() == Status.WAITS) {
                waitingTime += cu.getWaitingTime();
            }

            if (cu.getStatus() == Status.DONE & sv.getWaiting() != null) {
                
                ArrayList<Object> waitingResult = processWaiting(sv);
                sv = (Server) waitingResult.get(0);
                Customer afterWaiting = (Customer) waitingResult.get(1);

                served++;
                log.add(afterWaiting);
            }


            if (cu.getStatus() != Status.LEAVES & 
                    cu.getStatus() != Status.DONE) {

                ArrayList<Object> result = processEvent(cu, sv);

                if (result != null & result.size() == 2) {
                    
                    sv = (Server) result.get(0);
                    Customer nextEvent = (Customer) result.get(1);

                    if (nextEvent.getStatus() == Status.SERVED) {
                        served++;
                    }

                    log.add(nextEvent);
                }
            }

            finalLog.add(cu);
            log.remove(cu);
            Collections.sort(log);
        }

        printing(finalLog);

        int notServed = count - served;
        String avgWaiting = String.format("%.3f", (waitingTime / served));

        System.out.println("[" + avgWaiting + " " + served + " " + 
                notServed + "]" ); 
    }

    public static void printing(ArrayList<Customer> cust) {
        if (cust.size() > 0) {
            for (Customer c: cust) {
                System.out.println(c);
            }
        }
    }

    public static ArrayList<Object> processEvent(Customer cu, Server sv) {

        ArrayList<Object> list = new ArrayList<Object>(2);

        if (cu.getStatus() == Status.ARRIVES) {
            if (sv.serve(cu) != null) {

                list.add(sv.serve(cu)); 
                list.add(cu.switchStatus("serve", cu.getArrivalTime()));

            } else {
                if (sv.wait(cu) != null) {

                    list.add(sv.wait(cu));
                    double waitingTime = sv.getCompletionTime() - 
                        cu.getArrivalTime();
                    list.add(cu.switchStatus("wait", waitingTime));

                } else if (sv.wait(cu) == null) {

                    list.add(sv);
                    list.add(cu.switchStatus("", 0));
                }

            }
        } else if (cu.getStatus() == Status.SERVED) {

            list.add(sv);
            double serviceTime = sv.getServiceTime();
            list.add(cu.served(serviceTime));

        }
        return list;

    }

    public static ArrayList<Object> processWaiting(Server sv) {

        ArrayList<Object> list = new ArrayList<Object>(2);

        Customer cu = sv.getWaiting();
        double newStartTime = sv.getCompletionTime();
        cu = new Customer(cu.getID(), newStartTime);
        Server newSv = new Server();

        list.add(newSv.serve(cu));
        list.add(cu.switchStatus("serve", cu.getArrivalTime()));

        return list;

    }


}
