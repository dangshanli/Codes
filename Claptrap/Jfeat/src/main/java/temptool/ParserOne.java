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
        System.out.println("title:"+doc.title());
//        System.out.println(doc.toString());
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

    /**
     *
     * @param document 第一页html的document
     * @param directory 目录
     */
    public void resolveWnacg(Document document,String directory){
        //create directory if not exists
        if (!Files.exists(Paths.get(directory))) {
            try {
                Files.createDirectory(Paths.get(directory));
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

        //get addr and download
        String firstPicAddr = "http:"+ document.getElementById("picarea").attributes().get("src");
        String head = firstPicAddr.substring(0,firstPicAddr.lastIndexOf("/") +1);
        System.out.println("pic header:"+head);
        int counter = document.getElementsByClass("pageselect").get(0).getElementsByTag("option").size();
        CountDownLatch latch = new CountDownLatch(counter);
        System.out.println("all counter:"+counter);

        for (int i = 0; i < counter; i++) {
            String fName = tail(counter,i+1);
            executorService.submit(() -> {
                try {
                    Connection.Response response = Jsoup.connect(head+fName).userAgent("Mozilla").ignoreContentType(true).execute();
                    Files.copy(response.bodyStream(), Paths.get(directory + fName), StandardCopyOption.REPLACE_EXISTING);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } finally {
                    System.out.println(fName+" done.");
                    latch.countDown();
                }
            });
        }
        try {
            latch.await(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 猜测图片名称
     * @param total
     * @param current
     * @return
     */
    String tail(int total,int current){
        int totalLength = String.valueOf(total).length();
        int currentLength = String.valueOf(current).length();
        String result = String.valueOf(current);
        while (totalLength > currentLength){
            currentLength++;
            result = "0"+result;
        }
        return result+".jpg";
    }

    public static void main(String[] args) {
        ParserOne parserOne = new ParserOne();
        try {
//            parserOne.resolveFallenLady(parserOne.getDocument("https://allporncomic.com/porncomic/fallen-lady-jared999da/8-1-fallen-lady-chapter-8-jared999d/"));
            parserOne.resolveWnacg(
                    parserOne.getDocument("http://www.wnacg.com/photos-view-id-16075938.html"),
                    "E:\\Downloads\\[ジンナイ] 監獄アカデミア THE COMIC [不咕鸟汉化组]\\");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parserOne.shutdown();
        }
    }
}
