import java.util.stream.Stream;
class Stats {

    final double sum;
    final double n;
    final double min;
    final double max;

    Stats(double sum, double n, double min, double max) {
        this.sum = sum;
        this.n = n;
        this.min = min;
        this.max = max;
    }

    public double getResult() {
        double computed = (sum/n - min)/(max - min);
        if (min == max) {
            return 0;
        }
        return computed;
    }
}


public class Main {
    static double normalizedMean(Stream<Integer> stream) {
        return stream.mapToDouble(i -> (double) i)
            .mapToObj(i -> new Stats(i, 1, i, i))
            .reduce((a,b) -> new Stats(a.sum + b.sum, a.n + b.n,
                        (a.min < b.min) ? a.min : b.min,
                        (a.max > b.max) ? a.max: b.max))
            .orElse(new Stats(0,0,0,0))
            .getResult();
    
    }
}
