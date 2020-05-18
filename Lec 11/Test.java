import java.time.Instant;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class UnitTask {
    int id;

    UnitTask() {
        this.id = new Random().nextInt(10);
    }

    int compute() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println(name + " : start");
            Thread.sleep(id * 1000);
            System.out.println(name + " : end");
        } catch (InterruptedException e) {
        }
        return id;
    }
}

class Sync {
    public static void main(String[] args) {
        System.out.println("Before calling compute()");
        new UnitTask().compute();
        System.out.println("After calling compute()");
    }
}

class Async {
    static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        System.out.println("Before calling compute()");
        Thread t = new Thread(() -> new UnitTask().compute());
        t.start();
        System.out.println("After calling compute()");
        System.out.println("Do independent task...");
        wait(5000);
        System.out.println("Done independent task...");
        while (t.isAlive()) {
            wait(1000);
            System.out.print(".");
        }
        System.out.println("compute() completes");
    }
}

public class Test {

    static void test() {

        Instant start, stop;
        start = Instant.now();

        Thread t = new Thread(() -> doSomething());
        try {
            Thread.sleep(100);
            t.start();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        stop = Instant.now();

        long timeInMillis = Duration.between(start, stop).toMillis();
        System.out.println(timeInMillis);
    }

    public static void doSomething() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

}

class Async2 implements Listener {
    void doAsync() {
        Thread t = new Thread(() -> {
            new UnitTask().compute();
            notifyListener();
        });
        t.start();
    }

    public void notifyListener() {
        System.out.println("compute() completed");
    }

    public static void main(String[] args) {
        Async2 async = new Async2();
        async.doAsync();
        System.out.println("Do something else...");
    }

}

class CF {
    static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        System.out.println("Before calling compute()");
        Instant start = Instant.now();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> new UnitTask().compute());
        try {
            System.out.println("Do independent task...");
            wait(5000);
            System.out.println("Done independent task...");
            Integer result = future.get();
            System.out.println("After compute(): " + result);
        } catch (InterruptedException | ExecutionException e) {
        }

        Instant stop = Instant.now();
        long timeInMillis = Duration.between(start, stop).toMillis();
        System.out.println(timeInMillis);

    }

}

class CF2 {

    static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
    public static void main(String[] args) {
        System.out.println("Before calling compute()");
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> new UnitTask().compute())
                .thenAccept(s -> System.out.println("After compute(): " + s));
        System.out.println("Do independent task");
        wait(5000);
        System.out.println("Done independent task");
        future.join();

    }

}
