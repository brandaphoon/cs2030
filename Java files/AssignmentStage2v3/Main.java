import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        int num = sc.nextInt();

        ArrayList<Server> servers = new ArrayList<Server>();
        
        ArrayList<Server> schedule = new ArrayList<Server>();

        servers.add(new Server(1));

        for (int i = 0; i < num + 1; i++) {
            String inputCustomer = sc.nextLine();
            String[] parts = inputCustomer.split(" ");

            if (parts.length == 2) {

                SmallCustomer c = new SmallCustomer(parts[0], 
                        Integer.parseInt(parts[1]));
            
                ArrayList<ArrayList<Server>> results = serveCustomer(servers,
                        schedule,c);

                servers = results.get(0);
                schedule = results.get(1);

            } else if (parts.length == 4) {
                BigCustomer bc = new BigCustomer(parts[0], 
                        Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]));
                
                ArrayList<ArrayList<Server>> results = serveBigCustomer(servers, 
                        schedule,bc);
 
                servers = results.get(0);
                schedule = results.get(1);

                }
        }

        for (Server l: schedule) {
            System.out.println(l);
        }

        sc.close();
    }

    public static ArrayList<ArrayList<Server>> serveCustomer(ArrayList<Server>
            servers, ArrayList<Server> schedule, Customer c) {

        ArrayList<ArrayList<Server>> list = new ArrayList<ArrayList<Server>>(2);

        boolean served = false;

        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).canServe(c)) {
                served = true;
                schedule.add(servers.get(i).serve(c));
                servers.set(i, servers.get(i).serve(c));
                break;
            }
        }

        if (served == false) {
            Server l = new Server(servers.size() + 1);
            schedule.add(l.serve(c));
            servers.add(l.serve(c));
        }

        list.add(servers);
        list.add(schedule);

        return list;
    }

           
}
