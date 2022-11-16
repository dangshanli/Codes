package example;

import org.assertj.core.util.Strings;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 语雀迁移笔记到obsidian
 * 涉及将原本存储在语雀服务器上图片拷贝到本地，实现完全迁移，而不是文本迁到本地，图片还在阿里服务器上
 * <p>
 * 思路：
 * 0 使用正则匹配语雀域名的https图片地址
 * 1 使用该地址下载图片文件到本地
 * 2 替换md文件图片地址为本地地址
 */
public class ImgReplacer {

    static Pattern p = Pattern.compile("!\\[(.*)\\]\\((https://cdn.nlark.com/yuque[^!\\[\\]\\)]+)\\)");

    public void replaceAllPath(List<String> paths) {
        paths.stream()
                .forEach(path -> {
                    try {
                        replaceOneFile(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("文件路径不对。读、写失败,path=" + path);
                    }
                });

    }

    // 对单文件做图片地址替换
    public void replaceOneFile(String path) throws IOException {
        System.out.println("--------------------------------处理" + path + "--------------------------------");
        String s = Files.readString(Paths.get(path));
        Matcher matcher = p.matcher(s);
        if (!matcher.find()) {
            System.out.println(path + " 没有匹配到语雀图片模式");
            return;
        }
        String s2 = matcher.replaceAll(matchResult -> {
            String source = matchResult.group(0);
            System.out.println("source:" + source);
            String imgName = matchResult.group(1);
            String imgUrl = matchResult.group(2);
            if (imgName.contains(" ")) {
                imgName = imgName.replace(" ", "_");
            }
            if (Strings.isNullOrEmpty(imgName)) {
                imgName = UUID.randomUUID().toString();
            }

            try {
                download(imgUrl, imgName);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
                throw new RuntimeException("Error downloading");
            }
            return "![" + imgName + "](assets/{})".replace("{}", imgName + ".png");
        });
        Files.write(Paths.get(path), s2.getBytes(StandardCharsets.UTF_8));
        System.out.println("--------------------------------\n");
    }


    // 下载https://cdn.nlark.com/yuque下的图片文件
    void download(String url, String imgName) throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("url: " + url);
        System.out.println("imgName: " + imgName);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenAccept(action -> {
                    byte[] bb = action.body();
                    System.out.println(bb.length);

                    try {
                        Files.write(Paths.get("D:\\obsidiens\\assets\\" + imgName + ".png"), bb);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).get(10, TimeUnit.SECONDS);

        System.out.println("下载并写入文件到->D:/obsidiens/assets/" + imgName + ".png");
    }

    // 对指定目录下所有文件做扫描 获取其中的语雀图片链接
    public List<String> collectPathsFromDir(String dir) throws IOException {
        Path dirPath = Paths.get(dir);
        List<String> paths = new ArrayList<String>();
        Files.walkFileTree(dirPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);
                if (file.getFileName().toString().endsWith(".md")) {
                    paths.add(file.toAbsolutePath().toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return paths;
    }

    public static void main(String[] args) {
        ImgReplacer replacer = new ImgReplacer();
        List<String> files;
        try {
            files = replacer.collectPathsFromDir("D:\\obsidiens\\计算机世界\\算法");
            System.out.println(files);
            System.out.println(files.size());
            replacer.replaceAllPath(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
