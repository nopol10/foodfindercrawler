import org.jsoup.Jsoup;
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
    protected boolean processWebsiteData(Document doc) throws Exception {
        Elements results = doc.select("div.pagination-number > div > b");
        String linkText = results.last().text();
        int pageCount = Integer.parseInt(linkText);
        for(int i=1; i<=pageCount;i++){
            String newSearchURL = searchURL + "?page=" + Integer.toString(i);
            doc = Jsoup.connect(newSearchURL).timeout(0).userAgent("Mozilla/5.0").get();
            results = doc.select("div.col-s-12 > a.result-title");

            for (Element result : results) {
                String linkHref = result.attr("href");
                insertUrlToMemory(linkHref);
            }
        }
        return isValid;
    }

    @Override
    public void run() {
        System.out.println("In Zomato Crawler "+index);
        runMainLoop();

    }
}
