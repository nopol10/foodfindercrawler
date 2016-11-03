/**
 * Author: David Zhao Han
 */

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class RestaurantSearchJava {
    private static final String HUNGRYGOWHERE_CHINESE_SEARCH_URL = "http://www.hungrygowhere.com/cuisine/chinese/";
    private static final String HUNGRYGOWHERE_WESTERN_SEARCH_URL = "http://www.hungrygowhere.com/cuisine/western/";
    private static final String HUNGRYGOWHERE_HALAL_SEARCH_URL = "http://www.hungrygowhere.com/cuisine/halal/";
    private static final String TRIPADVISOR_SEARCH_URL = "https://www.tripadvisor.com.sg/RestaurantSearch?Action=PAGE&geo=294265&ajax=1&itags=10591&sortOrder=popularity";
    private static final String ZOMATO_KL_LUNCH_SEARCH_URL = "https://www.zomato.com/kuala-lumpur/lunch";
    private static final String ZOMATO_KL_DINNER_SEARCH_URL = "https://www.zomato.com/kuala-lumpur/dinner";

    public static void search() throws IOException {
//        searchHungryGoWhere();
//        searchTripAdvisor();
        searchZomato();
    }

    public static void searchHungryGoWhere() throws IOException {
        getRestaurantURLsHGW(HUNGRYGOWHERE_CHINESE_SEARCH_URL);
        getRestaurantURLsHGW(HUNGRYGOWHERE_WESTERN_SEARCH_URL);
        getRestaurantURLsHGW(HUNGRYGOWHERE_HALAL_SEARCH_URL);
    }

    public static void searchTripAdvisor() throws IOException {
        getRestaurantURLsTA(TRIPADVISOR_SEARCH_URL);
    }

    public static void searchZomato() throws IOException {
        getRestaurantURLsZO(ZOMATO_KL_LUNCH_SEARCH_URL);
        getRestaurantURLsZO(ZOMATO_KL_DINNER_SEARCH_URL);
    }




    // ZO
    public static void getRestaurantURLsZO(String searchURL) throws IOException {
        int pageCount = getRestaurantCountZO(searchURL);
        for(int i=1; i<=pageCount;i++){
            String newSearchURL = searchURL + "?page=" + Integer.toString(i);
            Document doc = Jsoup.connect(newSearchURL).timeout(0).userAgent("Mozilla/5.0").get();
            Elements results = doc.select("div.col-s-12 > a.result-title");

            for (Element result : results) {
                String linkHref = result.attr("href");
                String linkText = result.text();
                System.out.println(linkText + ", " + linkHref);
            }
        }
    }

    public static int getRestaurantCountZO(String searchURL) throws IOException {
        Document doc = Jsoup.connect(searchURL).timeout(0).userAgent("Mozilla/5.0").get();
        Elements results = doc.select("div.pagination-number > div > b");
        String linkText = results.last().text();
        return Integer.parseInt(linkText);
    }




    // TA
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




    // HGW
    public static void getRestaurantURLsHGW(String searchURL) throws IOException {
        int restaurantCount = getRestaurantCountHGW(searchURL);
        int pageCount = restaurantCount/6 + 1;
        for(int i=1; i<=pageCount;i++){
            String newSearchURL = searchURL + "?page_number=" + Integer.toString(i);
            Document doc = Jsoup.connect(newSearchURL).timeout(0).userAgent("Mozilla/5.0").get();
            Elements results = doc.select("h2.hneue-bold-mm > a");

            for (Element result : results) {
                String linkHref = result.attr("href");
                String linkText = result.text();
                System.out.println(linkText + ", http://www.hungrygowhere.com" + linkHref);
            }
        }

    }

    public static int getRestaurantCountHGW(String searchURL) throws IOException {
        // without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).timeout(0).userAgent("Mozilla/5.0").get();
        // select relevant html elements
        Elements results = doc.select("div.search-result-head > span");
        String linkText = results.first().text();
        return Integer.parseInt(linkText.split(" ")[0]);
    }
}
