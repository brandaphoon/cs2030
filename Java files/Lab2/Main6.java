import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main6 {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        int num = sc.nextInt();

        ArrayList<Loader> loaders = new ArrayList<Loader>();
        
        ArrayList<Loader> schedule = new ArrayList<Loader>();

        loaders.add(new Loader(1));

        for (int i = 0; i < num + 1; i++) {
            String inputCruise = sc.nextLine();
            String[] parts = inputCruise.split(" ");

            if (parts.length == 2) {

                SmallCruise c = new SmallCruise(parts[0], 
                        Integer.parseInt(parts[1]));
            
                ArrayList<ArrayList<Loader>> results = serveCruise(loaders,
                        schedule,c);

                loaders = results.get(0);
                schedule = results.get(1);

            } else if (parts.length == 4) {
                BigCruise bc = new BigCruise(parts[0], 
                        Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]));
                
                ArrayList<ArrayList<Loader>> results = serveBigCruise(loaders, 
                        schedule,bc);
 
                loaders = results.get(0);
                schedule = results.get(1);

                }
        }

        for (Loader l: schedule) {
            System.out.println(l);
        }

        sc.close();
    }

    public static ArrayList<ArrayList<Loader>> serveCruise(ArrayList<Loader>
            loaders, ArrayList<Loader> schedule, Cruise c) {

        ArrayList<ArrayList<Loader>> list = new ArrayList<ArrayList<Loader>>(2);

        boolean served = false;

        for (int i = 0; i < loaders.size(); i++) {
            if (loaders.get(i).canServe(c)) {
                served = true;
                schedule.add(loaders.get(i).serve(c));
                loaders.set(i, loaders.get(i).serve(c));
                break;
            }
        }

        if (served == false) {
            if ((loaders.size() + 1) % 3 != 0) {
                Loader l = new Loader(loaders.size() + 1);
                schedule.add(l.serve(c));
                loaders.add(l.serve(c));
            } else {
                RecycledLoader rc = new RecycledLoader(loaders.size() + 1);
                schedule.add(rc.serve(c));
                loaders.add(rc.serve(c));
            }
        }

        list.add(loaders);
        list.add(schedule);

        return list;
    }

    public static ArrayList<ArrayList<Loader>> serveBigCruise(ArrayList<Loader>
            loaders, ArrayList<Loader> schedule, BigCruise bc) {
        
            ArrayList<ArrayList<Loader>> list = new ArrayList<ArrayList<Loader>>();
            
            ArrayList<Loader> l = loaders;
            ArrayList<Loader> s = schedule;

            int noOfLoads = bc.getNumOfLoadersRequired();

            for(int i = 0; i < noOfLoads; i++) {
                list = serveCruise(l, s, bc);
                l = list.get(0);
                s = list.get(1);
            }

            return list;
               
    }
           
}
