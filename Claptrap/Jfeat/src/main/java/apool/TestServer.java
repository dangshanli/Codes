package apool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TestServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8333);
        Executors.newSingleThreadExecutor().submit(()->{
            while (true){
                try(Socket socket = serverSocket.accept()) {
                    System.out.println("收到请求");
                    InputStream inputStream = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    System.out.println(inputStream.available());
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    System.out.println(new String(bytes));
                    out.write("ok\n".getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }
            }

        });
    }
}
