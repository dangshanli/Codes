package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureSubmit {
    static int f(int x) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.multiplyExact(x,5);
    }

    public static void main(String[] args) {
//        System.out.println(f(3));
        int a = 3;

        CompletableFuture<Integer> iFuture = new CompletableFuture<>();
        iFuture.complete(f(a));
        try {
            int result = iFuture.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
