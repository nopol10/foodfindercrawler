import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Crawls the Zomato website for useful links
 */
public class ZomatoCrawler extends CrawlerBase {


    public ZomatoCrawler(String initialSite, int i) {
        super(initialSite);
        index = i;
    }

    @Override
    protected boolean processWebsiteData(Document doc) {
        // Verify that the page is a restaurant page
        boolean isValid = false;
        Elements isRestaurantElement = doc.select("meta[content=\"zomatocom:restaurant\"]");
        if (isRestaurantElement != null && isRestaurantElement.size() > 0) {
            System.out.println("Is restaurant page!");
            isValid = true;
        }
        Elements results = doc.select("a");
        // Look for all hrefs in the page
        for (Element result : results) {
            String linkHref = result.attr("abs:href");
            insertUrlToMemory(linkHref);
        }
        return isValid;
    }

    @Override
    public void run() {
        System.out.println("In Zomato Crawler "+index);
        runMainLoop();

    }
}
