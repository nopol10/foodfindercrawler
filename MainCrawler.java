import java.io.IOException;

public class MainCrawler {

    public static String[] zomatoInitialSites = {
      "https://www.zomato.com/selangor/spice-road-usj",
      "https://www.zomato.com/selangor/fatbaby-ice-cream-ss-15",
    };

    public static Thread[] threads;

    public static void main(String[] args) throws IOException {
        threads = new Thread[20];
        for (int i=0; i<threads.length; i++) {
            Thread newThread = new Thread(new ZomatoCrawler(zomatoInitialSites[0], i));
            newThread.start();
            threads[i] = newThread;
        }

        for(int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        CrawlerBase.saveDataToFile();
    }
}
