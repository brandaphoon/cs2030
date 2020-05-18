import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.ArrayList;

class Main {
    
    static boolean isPrime(int x) {
        if (x == 0 || x == 1) {
            return false;
        }
        
        return IntStream.range(2, x).noneMatch(i -> x % i == 0);
    }

    static int[] twinPrimes(int n) {
        return IntStream.rangeClosed(1, n)
            .filter(x -> isPrime(x))
            .filter(i -> isPrime(i - 2) || isPrime(i + 2))
            .toArray();
    }

    static int gcd(int m, int n) {
        return Stream.iterate(new Pair(m,n), i -> i.getS() != 0,
            j -> new Pair(j.getS(), j.getF() % j.getS()))
            .reduce((f,s) -> s).get().getS();
    }

    /* static long countRepeats(int... array) {
   
        int[] l1 = IntStream.rangeClosed(1, array.length-1)
            .filter(i -> array[i] == array[i - 1])
            .toArray();
  
       int[] l2 = IntStream.rangeClosed(0, l1.length-2)
            .filter(i -> l1[i+1] - l1[i] != 1).toArray();
        
        if(l1.length == 0) {
            return 0;
        }

        if (l2.length == 0) {
            return 1;
        } else {
            return l1.length;
        }

    } */


    /* static double normalizedMean(Stream<Integer> stream) {
        List<Integer> list = stream.collect(Collectors.toList());
        Integer min = list.stream().min(Integer::compare).get();
        Integer max = list.stream().max(Integer::compare).get();
        Integer sum = list.stream().reduce((i,j) -> i + j).get();
        long count = list.stream().count();
        return ((sum/count) - min)/ (max - min);
    
    } */
    
}
