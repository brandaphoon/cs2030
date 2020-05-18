import java.util.List;
import java.util.function.BiFunction;
import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.LongStream;
import java.util.function.BiFunction;

public class Rec7v2 {

    static boolean isPrime(int n) {
        return IntStream
            .range(2,n)
            .noneMatch(x -> n % x == 0 );
    }

    static IntStream factors(int n) {
        return IntStream
            .rangeClosed(2,n)
            .filter(x -> n % x == 0);
    }

    static IntStream PrimeNumbers(int n) {
        return factors(n)
            .filter(x -> isPrime(x));
    }
    
    static LongStream omega(int n) {
        return IntStream
            .range(0, n+1)
            .mapToLong(x -> PrimeNumbers(x).count());
    }

    
}