import java.util.List;
import java.util.function.BiFunction;
import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.LongStream;
import java.util.function.BiFunction;
//This is for large Integer values
public class Rec7 {

    static boolean isPrime(int n) {
        return IntStream
            .range(2,n)
            .noneMatch(x -> n % x == 0);
    }

    static IntStream primeNumbers(int x) {
        return factors(x)
            .filter(s -> isPrime(s));
    }

    static IntStream factors(int x) {
        return IntStream
            .rangeClosed(2, x)
            .filter(i -> x % i == 0);
    }

    static LongStream omega(int n) {
        return IntStream
            .range(1 ,n + 1)
            .mapToLong(x -> primeNumbers(x).count());
    }

    static Stream<BigInteger> fib(int n) {
        return Stream.iterate(
            new Pair<>(BigInteger.ZERO, BigInteger.ONE),
            p -> new Pair<>(p.second, p.first.add(p.second))
            ).map(p -> p.second).limit(n);
    }

    static <T,U,R>  Stream<R> product(List<? extends T> l1, List<? extends U> l2, BiFunction<? super T, ? super U, R> func) {
        return l1.stream()
            .flatMap(x -> l2.stream().map( y -> func.apply(x,y))); 
    }
}

class Pair<T> {
    T first;
    T second; 

    Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
}