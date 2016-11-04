import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HGWCrawler extends CrawlerBase {

    private int startPage, endPage;

    public HGWCrawler(String initialSite, int i, int startPage, int endPage) {
        super(initialSite);
        index = i;
        this.startPage = startPage;
        this.endPage = endPage;
    }

    public static int getRestaurantCountHGW(String searchURL) throws IOException {
        // without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).timeout(0).userAgent("Mozilla/5.0").get();
        // select relevant html elements
        Elements results = doc.select("div.search-result-head > span");
        String linkText = results.first().text();
        return Integer.parseInt(linkText.split(" ")[0]);
    }

    @Override
    protected boolean processWebsiteData(Document doc) {
        // Verify that the page is a restaurant page
        boolean isValid = false;
        Elements isRestaurantElement = doc.select(".module-ibl-summary");
        if (isRestaurantElement != null && isRestaurantElement.size() > 0) {
            System.out.println("HGW Is restaurant page!");
            isValid = true;
        }
        Elements results = doc.select("a");

        for (Element result : results) {
            String linkHref = result.attr("abs:href");
            insertUrlToMemory(linkHref);
        }
        return isValid;
    }

    protected void runMainLoop() {
        boolean justStarted = true;
        for(int i=startPage; i < endPage; i++){
            String newSearchURL = initialSite + "?page_number=" + Integer.toString(i);
            Document doc = null;
            try {
                doc = Jsoup.connect(newSearchURL).timeout(0).userAgent("Mozilla/5.0").get();
                Elements results = doc.select("h2.hneue-bold-mm > a");

                for (Element result : results) {
                    String linkHref = result.attr("abs:href");
                    goodUrls.add(cleanUrl(linkHref));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        System.out.println("In HGW Crawler "+index);
        runMainLoop();

    }

}
