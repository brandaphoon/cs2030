import java.util.stream.IntStream;
import java.util.Scanner;

public class intStream {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        int j = sc.nextInt();

    }

    static void tutor(int i, int j) {
        IntStream.of(i, j)
            .map(x -> new Stats(x))
            .
        
    }
}

class Stats {
    int ones;
    int tens;
    int hundreds;
    int thousands;

    Stats(int num) {
        this.ones = num % 10;
        this.tens = (num % 100) / 10;
        this.hundreds = (num % 1000) / 100;
        this.thousands = (num % 10000) / 1000;
    }

}