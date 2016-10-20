/**
 * Author: David Zhao Han
 */

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GoogleSearchJava {
    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    private static final String SEARCH_TERM = "food review Singapore";
    private static final int NUM_RESULT = 100;

    public static void main(String[] args) throws IOException {
        String searchURL = GOOGLE_SEARCH_URL + "?q="+SEARCH_TERM+"&num="+NUM_RESULT;
        // without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();

        // select relevant html elements
        Elements results = doc.select("h3.r > a");

        for (Element result : results) {
            String linkHref = result.attr("href");
            String linkText = result.text();
            System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
        }
    }
}
