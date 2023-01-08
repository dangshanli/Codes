package example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class DeleteTcpFile {


    void action() throws IOException {
        String path = "D:\\break_the_wall\\Surfshark_Config";
        String tail = "tcp.ovpn";

        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);

//                if (file.getFileName().toString().endsWith(tail) && attrs.isRegularFile()) {
//                    System.out.println(file.getFileName());
//                    Files.move(file, Paths.get("D:\\break_the_wall\\Surfshark_Config\\tcp\\" + file.getFileName()));
//                }

                if (file.getFileName().toString().contains("-st0") && attrs.isRegularFile()) {
                    System.out.println(file.getFileName());
                    Files.move(file, Paths.get("D:\\break_the_wall\\Surfshark_Config\\\\static\\" + file.getFileName()));
                }
                return FileVisitResult.CONTINUE;
            }
        });

    }

    public static void main(String[] args) throws IOException {
        new DeleteTcpFile().action();
    }
}
