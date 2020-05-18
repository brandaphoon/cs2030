import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        if (args.length != 0) {
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", args[0]);
        }

        System.out.println("Number of worker threads: " + ForkJoinPool.commonPool().getParallelism());

        Instant start = Instant.now();
        long howMany = IntStream.range(2000000, 3000000).parallel().filter(x -> isPrime(x)).count();
        Instant stop = Instant.now();

        System.out.println(howMany + " : " + Duration.between(start, stop).toMillis() + "ms");

        int sum = IntStream.of(1, 2, 3, 4, 5).parallel().filter(x -> {
            System.out.println("filter: " + x + " " + Thread.currentThread().getName());
            return x % 2 == 1;
        }).map(x -> {
            System.out.println("map: " + x + " " + Thread.currentThread().getName());
            return x;
        }).reduce(0, (x, y) -> {
            System.out.println("reduce: " + x + " + " + y + " " + Thread.currentThread().getName());
            return x + y;
        });
        System.out.println(sum);
    }

    static boolean isPrime(int n) {
        return IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(x -> n % x == 0);
    }

}

class OneSecondTask {
    int ID;

    public OneSecondTask(int ID) {
        this.ID = ID;
    }

    public int compute() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ID;
    }

    public static void runTasks(Stream<OneSecondTask> tasks) {
        Instant start = Instant.now();
        List<Integer> result = tasks.map(x -> x.compute()).collect(Collectors.toList());
        Instant stop = Instant.now();
        System.out.print(result + " ");
        System.out.println(Duration.between(start, stop).toMillis() + "ms");
    }

    public void tasks() {
        Stream<OneSecondTask> tasks = IntStream.range(0, 10).mapToObj(x -> new OneSecondTask(x));
        runTasks(tasks);
    }


    public static void try1() {
        int sum = Stream.of(1, 2, 3, 4, 5).parallel().filter(x -> { System.out.println("filter: " + x + " " + Thread.currentThread().getName());
                    return x % 2 == 1;}).reduce(0, (x, y) -> { System.out.println("accumulate: " + x + " + " + y + " " + Thread.currentThread().getName()); return x + y;}, (x, y) -> { System.out.println("combine: " + x + " + " + y + " "+ Thread.currentThread().getName()); return x + y;});
            }
    


}