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
   
}
