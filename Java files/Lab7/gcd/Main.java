import java.util.stream.Stream;

class Pair {
    private final int first;
    private final int second;

    public Pair(int f, int s) {
        this.first = f;
        this.second = s;
    }

    public int getF() {
        return this.first;
    }

    public int getS() {
        return this.second;
    }
}

public class Main {

    static int gcd(int m, int n) {
        return Stream.iterate(new Pair(m,n),
            j -> new Pair(j.getS() % j.getF(), j.getF()))
            .filter(i -> i.getF() == 0)
            .findFirst().get().getS();
    } 

}


