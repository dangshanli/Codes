package apool;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DoDoServer {

    ExecutorService executor = new ThreadPoolExecutor(
            10,
            100,
            5,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100));

    void start() {
        ServerSocket serverSocket = aServer();
        while (true) {
            Socket activeSocket = null;
            try {
                activeSocket = serverSocket.accept();
                handleRequest(activeSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void handleRequest(Socket socket) throws IOException, Exception {
        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String command;
            while ((command = input.readLine()) != null) {
                if (command == null || command.length() == 0
                        || !command.contains(":")
                        || command.split(":").length != 2) {
                    output.write("Command:[" + command + "] is not a valid command,pls input [fibonacci:num] to calculate fib num\n");
                } else {
                    String subCommand = command.trim().split(":")[0].trim();
                    String subCommand2 = command.trim().split(":")[1].trim();
                    if (subCommand != null && subCommand.equals("fibonacci")) {
                        int fibCounter = Integer.valueOf(subCommand2);
                        String result = calculateFubSerial(fibCounter);
                        output.write(result + "\n");
                    } else {
                        output.write("do not support this command!!![" + command + "]\n");
                    }
                }
                output.flush();
            }
            socket.close();
        }
    }

    private String calculateFubSerial(int fibCounter) {
        StringBuilder sb = new StringBuilder();
        Map<String, Long> map = new TreeMap<>(Comparator.comparingInt(Integer::valueOf));
        calculateFubNum(fibCounter, map);
        new TreeMap<String, Long>(Comparator.comparingInt(Integer::valueOf));
        map.forEach((k, v) -> {
            sb.append(v + " ");
        });
        return sb.toString();
    }

    long calculateFubNum(int fibCounter, Map<String, Long> grid) {
        if (fibCounter == 0) {
            grid.put("0", 0l);
            return 0;
        }

        if (fibCounter == 1) {
            grid.put("1", 1l);
            return 1;
        }
        return grid.computeIfAbsent(String.valueOf(fibCounter), fin -> {
            int i = Integer.valueOf(fin);
            return calculateFubNum(i - 1, grid) + calculateFubNum(i - 2, grid);
        });
    }

    ServerSocket aServer() {
        ServerSocket server;
        try {
            server = new ServerSocket();
            InetSocketAddress endPoint = new InetSocketAddress("localhost", 12900);
            int waitQueueSize = 100;
            server.bind(endPoint, waitQueueSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return server;
    }


    public static void main(String[] args) {
        new DoDoServer().start();
    }
}
