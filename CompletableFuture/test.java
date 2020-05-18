import java.util.concurrent.CompletableFuture;
			
public class test {
    
    static void completedFutureExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        while (!cf.isDone()){
            System.out.println("message");
        }
    }


}