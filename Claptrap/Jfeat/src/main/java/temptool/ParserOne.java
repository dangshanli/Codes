package temptool;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 解析allporncomic.com的单页面
 */
public class ParserOne {
    ExecutorService executorService = Executors.newFixedThreadPool(16);

    public Document getDocument(String url) throws IOException {
        Document doc = null;

        doc = Jsoup.connect(url).userAgent("Mozilla").post();
        System.out.println(doc.title());
        return doc;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void resolveFallenLady(Document document) {
        Elements elements = document.getElementsByAttribute("data-src");
        CountDownLatch latch = new CountDownLatch(elements.size());
        elements.eachAttr("data-src").forEach(e -> {
            String fName = e.substring(e.lastIndexOf("/") + 1);
            System.out.println(fName);
            String path = "E:\\Downloads\\fallen-lady-8-1\\";
            if (!Files.exists(Paths.get(path))) {
                try {
                    Files.createDirectory(Paths.get(path));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            }
            executorService.submit(() -> {
                try {
                    Connection.Response response = Jsoup.connect(e).userAgent("Mozilla").ignoreContentType(true).execute();
                    Files.copy(response.bodyStream(), Paths.get(path + fName), StandardCopyOption.REPLACE_EXISTING);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        });
        try {
            latch.await(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ParserOne parserOne = new ParserOne();
        try {
            parserOne.resolveFallenLady(parserOne.getDocument("https://allporncomic.com/porncomic/fallen-lady-jared999da/8-1-fallen-lady-chapter-8-jared999d/"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parserOne.shutdown();
        }
    }
}
