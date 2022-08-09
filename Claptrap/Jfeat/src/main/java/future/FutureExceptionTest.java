package future;

import java.util.concurrent.*;

/**
 * 测试 CompletableFuture异常触发特性
 */
public class FutureExceptionTest {
    void comFutureException() {
        CompletableFuture<String> iFuture = new CompletableFuture<>();
        ExecutorService singleEs = Executors.newSingleThreadExecutor();
        singleEs.submit(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    if (i * i == 25)
                        throw new DodoException("dodo异常");
                    if (i == 9)
                        iFuture.complete((i + 1) + "end");
                }
            } catch (DodoException e) {
                iFuture.completeExceptionally(e);//出现异常时候 如果不调用这个 main线程的get会因为异常永久阻塞
            }
        });
        try {
            String s = iFuture.get();
            System.out.println(s+"-good");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {//该异常仍然会包裹内部真正的异常
            e.printStackTrace();
        }finally {
            singleEs.shutdown();
        }
    }

    class DodoException extends Exception {
        public DodoException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        new FutureExceptionTest().comFutureException();
    }

}
