import java.util.stream.*;
import java.util.ArrayList;
import java.util.List;

class Main {
    
    /*static int[] twinPrimes(int n) {
        int[] l1 = IntStream.rangeClosed(2, n).filter(x -> isPrime(x)).toArray();
        
        int[] indexes = IntStream.range(1, l1.length-1).filter(x -> l1[x+1] - l1[x] == 2 || l1[x] - l1[x-1] == 2).toArray();
        
        return IntStream.of(indexes).map(z -> l1[z]).toArray();
    }*/

    static int[] twinPrimes(int n) {
        return IntStream.rangeClosed(2,n)
            .filter(x -> isPrime(x))
            .filter(x -> isPrime(x + 2) || isPrime(x - 2))
            .toArray();
    }

    static boolean isPrime(int n) {
        return IntStream.range(2,n).noneMatch(x -> n % x == 0);
    }

    static int gcd(int m, int n){
        if (m == n) {
            return n;
        }
        return Stream.iterate(new Pair(m,n), j -> new Pair( j.getS() % j.getF(), j.getF()))
            .filter(x -> x.getF() == 0)
            .findFirst().get().getS();

            //Find first returns Optional, then get() returns the Pair, getS() returns the gcd

    }

    static long countRepeats(int... array) {
        return IntStream
            .rangeClosed(1, array.length-2)
            .filter(x -> (array[x+1] == array[x] && array[x-1] != array[x]) || 
            (x == 1 && array[x-1] == array[x]))
            .count();

    }

    static double normalizedMean(Stream<Integer> stream) {
        return stream.mapToDouble(x -> (double) x)
                        .mapToObj( m -> new Store(m,m,m,1))
                        .reduce((f,s) -> new Store(
                            (f.getMin() < s.getMin()) ? f.getMin() : s.getMin(),
                            (f.getMax() > s.getMax()) ? f.getMax() : s.getMax(),
                            f.getSum() + s.getSum(),
                            f.getCount() + 1
                        ))
                        .orElse(new Store(0,0,0,0))
                        .getResult();
    }


}


class Pair {
    int first;
    int second;

    Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
    int getF() {
        return this.first;
    }

    int getS() {
        return this.second;
    }


}

class Store {
    double min;
    double max;
    double sum;
    double count;

    Store(double min, double max, double sum, double count) {
        this.min = min;
        this.max = max;
        this.sum = sum;
        this.count = count;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getSum() {
        return this.sum;
    }

    public double getCount() {
        return this.count;
    }

    public double getResult() {
        if (min == max) {
            return 0;
        }
        return ((sum/count) - min)/(max - min);
    }


}

