import java.time.Instant;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.ArrayList;

/**
 * This program finds different ways one can travel by bus (with a bit of
 * walking) from one bus stop to another.
 *
 * @author: Ooi Wei Tsang
 * @version: CS2030 AY19/20 Semester 1, Lab 10
 */
public class Main {
  /**
   * The program read a sequence of (id, search string) from standard input.
   * 
   * @param args Command line arguments
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    Instant start = Instant.now();
    Scanner sc = new Scanner(System.in);

    List<CompletableFuture<String>> list = new ArrayList<>();
    while (sc.hasNext()) {
      BusStop srcId = new BusStop(sc.next());
      String searchString = sc.next();
      // System.out.println(BusSg.findBusServicesBetween(srcId,
      // searchString).description());
      CompletableFuture<String> find = BusSg.findBusServicesBetween(srcId, searchString).get().description();
      list.add(find);
    }

    CompletableFuture<?>[] res = list.toArray(new CompletableFuture<?>[list.size()]);
    CompletableFuture.allOf(res).join();

    // lopp thru
    for (CompletableFuture<?> l : res) {
      System.out.println(l.get());
    }
    sc.close();
    Instant stop = Instant.now();
    System.out.printf("Took %,dms\n", Duration.between(start, stop).toMillis());
  }
}