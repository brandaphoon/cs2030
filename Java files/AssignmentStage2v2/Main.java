import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        int noOfSv = sc.nextInt();

        ArrayList<Server> servers = createServers(noOfSv); 

        int count = 0;

        PriorityQueue<Customer> customers = new PriorityQueue<Customer>();

        while (sc.hasNext()) {
            if (sc.hasNextDouble()) {
                double s = sc.nextDouble();
                count++;
                Customer c = new Customer(count, s); 
                customers.add(c);
            } else {
                break;
            }
        }

        sc.close();

        PriorityQueue<Event> events = addArrivals(customers);

        printing(events);
        printing(servers);
    }

    public static <T> void printing(PriorityQueue<T> ts) {
        if (ts.size() > 0) {
            for (T t: ts) {
                System.out.println(t);
            }
        }
    }

    public static <T> void printing(ArrayList<T> ts) {
        if (ts.size() > 0) {
            for (T t: ts) {
                System.out.println(t);
            }
        }
    }
    
    public static PriorityQueue<Event> addArrivals(
            PriorityQueue<Customer> custs) {
        
        PriorityQueue<Event> events = new PriorityQueue<Event>();

        for (Customer cu: custs) {
            events.add(new Event(cu));
        }
        
        return events;
    }   

    public static ArrayList<Server> createServers(int num) {
        ArrayList<Server> servers = new ArrayList<Server>();
        
        for (int i = 1; i < (num + 1); i++) {
            servers.add(new Server(i));
        }

        return servers;
    }
}
            


