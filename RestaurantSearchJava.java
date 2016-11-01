/**
 * Author: David Zhao Han
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class RestaurantSearchJava {
    private static final String HUNGRYGOWHERE_CHINESE_SEARCH_URL = "http://www.hungrygowhere.com/search-results/?cuisine=Chinese";
    private static final String HUNGRYGOWHERE_WESTERN_SEARCH_URL = "http://www.hungrygowhere.com/search-results/?cuisine=Western";
    private static final String HUNGRYGOWHERE_HALAL_SEARCH_URL = "http://www.hungrygowhere.com/search-results/?cuisine=Halal";

    private static final int MAX_URLS = 500;
    private static int urlCount = 0;


    public static void search() throws IOException {
        Path path = Paths.get("urls.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            searchHungryGoWhere(writer);
            writer.close();
        }


    }

    public static void searchHungryGoWhere(BufferedWriter writer) throws IOException {
        getRestaurantURLs(HUNGRYGOWHERE_CHINESE_SEARCH_URL, writer);
        getRestaurantURLs(HUNGRYGOWHERE_WESTERN_SEARCH_URL, writer);
        getRestaurantURLs(HUNGRYGOWHERE_HALAL_SEARCH_URL, writer);

        System.out.println("- Ended -");
    }

    public static void getRestaurantURLs(String searchURL, BufferedWriter writer) throws IOException {
        int restaurantCount = getRestaurantCount(searchURL);
        int pageCount = restaurantCount/6 + 1;
        for(int i=1; i<=pageCount;i++){
            String newSearchURL = searchURL + "&page_number=" + Integer.toString(i);
            Document doc = Jsoup.connect(newSearchURL).timeout(0).userAgent("Mozilla/5.0").get();
            Elements results = doc.select("h3.rs-regular-sm > a");

            for (Element result : results) {
                if (urlCount > MAX_URLS) {
                    return;
                }
                String linkHref = result.attr("href");
                String linkText = result.text();

                urlCount++;
                writer.write("http://www.hungrygowhere.com" + linkHref);
                writer.newLine();
                System.out.println(linkText + ", http://www.hungrygowhere.com" + linkHref);
            }
        }
    }

    public static int getRestaurantCount(String searchURL) throws IOException {
        // without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).timeout(0).userAgent("Mozilla/5.0").get();
        // select relevant html elements
        Elements results = doc.select("div.search-result-filter > span");
        String linkText = results.first().text();
        return Integer.parseInt(linkText.split(" ")[0]);
    }
}
