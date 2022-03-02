package basic.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFile {
    public static void main(String[] args) throws IOException {
        Files.readAllBytes(Paths.get("D:\\Codes\\Claptrap\\ThriftFeature\\src\\main\\resources\\pass.txt"));
    }
}
