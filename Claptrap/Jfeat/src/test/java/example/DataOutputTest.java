package example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

// 测试「DataOutputStream」类
public class DataOutputTest {

//    @BeforeAll
    static void init() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8333);
        Executors.newSingleThreadExecutor().submit(()->{
            while (true){
                try(Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    OutputStream out = socket.getOutputStream()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    System.out.println(br.lines().collect(Collectors.joining()));
                    out.write("ok\n".getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }
            }

        });

    }


    // writeChars writeBytes writeUTF 都是比较棘手的方法
    @Test
    void hardMethods() throws FileNotFoundException {
        String path = "src/test/resources/kk.dat";
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(path));
        try {
            dos.writeChars("千年一刹");
            dos.writeBytes("hello world");
            dos.writeUTF("高堂明镜悲白发");

            DataInputStream dis = new DataInputStream(new FileInputStream(path));
            byte[] bytes = new byte[400];
            dis.read(bytes);

            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTelnetInput() throws IOException {
        Socket socket = new Socket("localhost",8333);
        InputStream in = socket.getInputStream();
        System.out.println(in.getClass());
        OutputStream outputStream = socket.getOutputStream();
        System.out.println(outputStream.getClass());
        outputStream.write("但愿人长久\n".getBytes(StandardCharsets.UTF_8));
        outputStream.write("千里共婵娟\n".getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        System.out.println(new String(in.readAllBytes()));
    }

    @Test
    void testReader() throws IOException {
        // 测试BufferedStream 和 BufferedReader
        String path = "src/test/resources/ye.txt";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

        String s;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        System.out.println(sb.toString());
        System.out.println("--------------------------------");
        BufferedReader bbr = new BufferedReader(
                new InputStreamReader(new FileInputStream(path)));
        int c;
        StringBuffer ssb = new StringBuffer();
        while ((c = bbr.read()) != -1){
            ssb.append((char)c);
        }
        System.out.println(ssb.toString());



    }





}
