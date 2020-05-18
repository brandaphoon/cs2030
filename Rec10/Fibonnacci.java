import java.time.Instant;
import java.time.Duration;
import java.math.BigInteger;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

class Fib {
    static void generateFib(List<BigInteger> fibs) {
        int k = fibs.size();
        fibs.addAll(Stream.iterate(0, i -> i < k - 1, i -> i + 1).parallel()
                .map(i -> fibs.get(k - 2).multiply(fibs.get(i)).add(fibs.get(k - 1).multiply(fibs.get(i + 1))))
                .collect(Collectors.toList()));
    }

    static BigInteger findFibTerm(int n) {
        List<BigInteger> fibList = new ArrayList<>();
        fibList.add(BigInteger.ONE);
        fibList.add(BigInteger.ONE);
        Instant start = Instant.now();
        while (fibList.size() < n) {
            generateFib(fibList);
        }
        Instant stop = Instant.now();
        System.out.println(Duration.between(start, stop).toMillis() + "ms");
        return fibList.get(n - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BigInteger result = findFibTerm(sc.nextInt());
        sc.close();
        System.out.println(result);
    }
}