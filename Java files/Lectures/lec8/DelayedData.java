import java.util.Scanner;
import java.util.stream.Stream;
import java.util.function.Supplier;

public class DelayedData { 
    private int index;
    private Supplier<Integer> input;

    public DelayedData(int index, Supplier<Integer> input) {
        this.index = index;
        this.input = input;
    }

    public String toString() {
        return index + " : " + input.get();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DelayedData[] data = new DelayedData[5];
        for (int i = 0; i < data.length; i++) {
            data[i] = new DelayedData(i, () -> sc.nextInt());
        }
        Stream.of(data)
            .filter(x -> x.index % 2 == 0)
            .forEach(System.out::println);
    }
}
