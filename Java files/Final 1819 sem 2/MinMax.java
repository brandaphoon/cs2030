import java.util.Optional;
import java.util.stream.Stream;

class MinMax {
    final int min, max;

    public MinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return min + ", " + max;
    }

    static Optional<MinMax> findMinMax(Stream<Integer> instream) {
        return instream.map(o -> new MinMax(o,o))
                .reduce((x,y) -> {
                    int min = Math.min(x.min,y.min);
                    int max = Math.min(x.max,y.max);
                    return new MinMax(min, max);
                } );
    }
}
