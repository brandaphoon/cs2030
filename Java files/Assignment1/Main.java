import java.util.Scanner;
import java.util.ArrayList;

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
               if(sv.serve(c) != null) {
                  sv = sv.serve(c);
                  customers.add(c.statusChange(true));
               } else {
                  customers.add(c.statusChange(false));
               }

           } else {
               break;
           }
          
        }

        sc.close();

        for (Customer c: customers) {
            System.out.println(c);
            
        }

        System.out.println("Number of customers: " + count); 
    }

}
