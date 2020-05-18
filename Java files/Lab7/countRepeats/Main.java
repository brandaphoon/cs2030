import java.util.stream.IntStream;
import java.util.Arrays;

public class Main {

     /*static long countRepeats(int... array) {
   
        int[] l1 = IntStream.rangeClosed(1, array.length-1)
            .filter(i -> array[i] == array[i - 1])
            .toArray();
  
       long l2 = IntStream.rangeClosed(0, l1.length)
            .filter(i -> i == l1.length ? l1[l1.length-1] - 
                    l1[l1.length-2] != 1 : l1[i] - l1[i-1] != 1).count();
        
       return l2;

    }*/

    static long countRepeats(int... array) {
        return IntStream.rangeClosed(1, array.length - 2)
            .filter(x -> (array[x] == array[x + 1] && array[x] != array[x - 1]
                        || x == 1 && array[x] == array[x - 1] )).count();
    }
}
