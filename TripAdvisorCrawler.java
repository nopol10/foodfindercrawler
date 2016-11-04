import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class TripAdvisorCrawler extends CrawlerBase {

    private int startOffset = 0;

    public TripAdvisorCrawler(String initialSite, int i) {
        super(initialSite);
        index = i;
//        try {
//            int resultPageCount = getRestaurantCountTA(initialSite);
//            startOffset = Math.min(i, resultPageCount);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void getRestaurantURLsTA(String searchURL) throws IOException {
        int restaurantCount = getRestaurantCountTA(searchURL);
        for(int i=0; i<=restaurantCount;i+=30){
            String newSearchURL = searchURL + "&o=a" + Integer.toString(i);
            Document doc = Jsoup.connect(newSearchURL).timeout(0).userAgent("Mozilla/5.0").get();
            Elements results = doc.select("h3.title > a");

            for (Element result : results) {
                String linkHref = result.attr("href");
                String linkText = result.text();
                System.out.println(linkText + ", https://www.tripadvisor.com.sg" + linkHref);
            }
        }
    }

    public static int getRestaurantCountTA(String searchURL) throws IOException {
        searchURL += "&o=a0";
        Document doc = Jsoup.connect(searchURL).timeout(0).userAgent("Mozilla/5.0").get();
        Elements results = doc.select("div.popIndexBlock > div");
        String linkText = results.first().text();
        String numberString = linkText.split(" ")[2];
        return Integer.parseInt(numberString.replaceAll(",", ""));
    }


    @Override
    protected boolean processWebsiteData(Document doc) {
        // Verify that the page is a restaurant page
        boolean isValid = false;
        System.out.println("TAC:"+doc.baseUri());
//        Elements isRestaurantElement = doc.select("meta[content=\"zomatocom:restaurant\"]");
        if (doc.baseUri().contains("Restaurant_Review")
                && !doc.baseUri().endsWith("#REVIEWS")) {
//            System.out.println("Is restaurant page!");
            isValid = true;
        }
        Elements results = doc.select("a");

        for (Element result : results) {
            String linkHref = result.attr("abs:href");
//            if (linkHref.contains("Restaurant_Review")) {
                insertUrlToMemory(linkHref);
//            }
        }
        return isValid;
    }
//
//    @Override
//    protected void runMainLoop() {
//        boolean justStarted = true;
//        while (!shouldStop) {
//            String crawlUrl = "";
//            if (justStarted) {
//                crawlUrl = initialSite;
//                synchronized (urlsToCrawlLock) {
//                    crawledUrls.add(crawlUrl);
//                }
//                justStarted = false;
//            } else {
//                crawlUrl = getOldestUrlToRead();
//            }
//
//            // Read the website and perform any processing (should override processWebsiteData in children)
//            if (isValidUrl(crawlUrl)) {
//                Document doc = getWebsiteData(crawlUrl);
//                if (doc != null) {
//                    if (processWebsiteData(doc) == true) {
//                        goodUrls.add(cleanUrl(crawlUrl));
//                    }
//                }
//            }
//
//            // Temporary break for testing
//            if (goodUrls.size() > 500) {
//                shouldStop = true;
//                System.out.println("Get out!!!");
//            }
//
//            long currentTime = System.currentTimeMillis();
//            if (currentTime - previousSaveTime >= SAVE_INTERVAL) {
//                previousSaveTime = currentTime;
//                saveDataToFile();
//            }
//        }
////        saveDataToFile();
//    }

    @Override
    public void run() {
        System.out.println("In Trip Advisor Crawler "+index);
        runMainLoop();

    }
}
