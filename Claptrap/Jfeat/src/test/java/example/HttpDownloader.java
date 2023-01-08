package example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpDownloader {


    void download() throws ExecutionException, InterruptedException, TimeoutException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cdn.nlark.com/yuque/0/2021/png/10381823/1630243647806-ab2b59a7-d512-4417-981f-8a6eca4b58dd.png#averageHue=%23f5f5f5&clientId=u7a979233-c91a-4&crop=0&crop=0&crop=1&crop=1&from=ui&id=u0a4db0d5&margin=%5Bobject%20Object%5D&name=%E5%9B%BE%E8%A1%A8%E7%A4%BA%E6%B3%95.PNG&originHeight=323&originWidth=812&originalType=binary&ratio=1&rotation=0&showTitle=false&size=43868&status=done&style=none&taskId=u2c1f1112-6fc9-4750-a734-5e64745d98b&title="))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenAccept(action -> {
                    byte[] bb = action.body();
                    System.out.println(bb.length);

                    try {
                        Files.write(Paths.get("E:\\bubu-----bubu.png"),bb);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).get(10, TimeUnit.SECONDS);

        System.out.println("完！！！");
    }


    public static void main(String[] args) {
        try {
            new HttpDownloader().download();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
