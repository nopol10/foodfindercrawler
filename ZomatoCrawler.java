import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


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
        if (isRestaurantElement != null && isRestaurantElement.size() > 0
                && !doc.baseUri().contains("/photos")
                && !doc.baseUri().contains("/reviews")
                && !doc.baseUri().contains("/menu")) {
            System.out.println("Is restaurant page!");
            isValid = true;
        }
        Elements results = doc.select("a");

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
