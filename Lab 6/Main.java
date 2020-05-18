import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {
    
    /**
     * Recieving inputs to add students to a roster, then retrieving grades 
     * based on input queries and printing out them out.
     */

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();
        
        //Roster roster = new Roster("Students");
        Roster[] roster = {new Roster("Students")};

        Stream.iterate(0, i -> i + 1)
                .limit(num + 1)
                .map(x -> sc.nextLine().split("\\s+"))
                .filter(y -> y.length == 4)
                .forEach(i -> roster[0] = addStudents(roster[0], i));

        /*for (int i = 0; i < num + 1; i++) {
            
            String input = sc.nextLine();
            String[] arrInput = input.split("\\s+");
            if (arrInput.length == 4) {
                roster = addStudents(roster, arrInput);
            }
        }*/

        Roster nRoster = roster[0];
        ArrayList<String> results = new ArrayList<String>();

        while (sc.hasNext()) {
            String s = sc.nextLine();
            String[] arr = s.split("\\s+");
            if (arr.length == 3) {
                results = processQueries(results,arr,nRoster);
            } else {
                break;
            }
        }

        sc.close();

        for (String e: results) {
            System.out.println(e);
        } 
    }

    /**
     * This method process the inputs of students with the modules they took,
     * the assessment name and the grade into a list of roster.
     */

    public static Roster addStudents(Roster roster, String[]
            input) {

        if (input.length == 4) {
            
            String id = input[0];
            String module = input[1];
            String assessmentName = input[2];
            String grade = input[3];


            if (roster.containsKey(id)) {
                if (roster.get(id).get().containsKey(module)) {
                    roster.get(id).get().get(module).get().put(
                            new Assessment(assessmentName, grade));
                } else {
                    roster.get(id).get().put(new Module(module).put(new Assessment(
                                    assessmentName, grade)));
                }
            } else {
                roster.put(new Student(id).put(new Module(module).put(
                                new Assessment(assessmentName, grade))));
            }
        }

        return roster;
    }

    /**
     * Process the input queries and retrieving the grades, if it returns
     * null it will be caught as NoSuchRecordException
     * and the message will be printed.
     */

    public static ArrayList<String> processQueries(ArrayList<String> results,
            String[] input, Roster roster) {
        
        if (input.length == 3) {
            
            String id = input[0];
            String module = input[1];
            String assessmentName = input[2];

            try {
                results.add(roster.getGrade(id, module, assessmentName));
            } catch (NoSuchRecordException e) {
                results.add("NoSuchRecordException: " + e.getMessage());
            }
        } 

        return results;
    }
}
        

