import java.io.IOException;

public class MainCrawler {

    public static String[] zomatoInitialSites = {
        "https://www.zomato.com/selangor/spice-road-usj",
        "https://www.zomato.com/selangor/fatbaby-ice-cream-ss-15",
        "https://www.zomato.com/id/selangor/dao-dao-dessert-house-bandar-kinrara",
        "https://www.zomato.com/id/kuala-lumpur/capricciosa-bukit-bintang",
        "https://www.zomato.com/selangor/spice-road-usj",
        "https://www.zomato.com/sk/selangor/sushi-mentai-ss-2",
        "https://www.zomato.com/id/selangor/carlyda-nasi-campur-seksyen-18",
        "https://www.zomato.com/selangor/meteora-usj",
        "https://www.zomato.com/selangor/the-porki-society-ss-2",
        "https://www.zomato.com/it/selangor/restoran-new-apollos-usj",
        "https://www.zomato.com/kuala-lumpur/the-chicken-rice-shop-mid-valley-city",
        "https://www.zomato.com/id/selangor/home-style-kitchen-asam-house-bandar-puteri",
        "https://www.zomato.com/selangor/murni-discovery-usj",
        "https://www.zomato.com/it/selangor/restoran-little-italy-puchong-jaya",
        "https://www.zomato.com/pt/selangor/aroma-dinings-ss-2",
        "https://www.zomato.com/selangor/robata-monkey-jaya-one-seksyen-13-petaling-jaya",
        "https://www.zomato.com/cs/selangor/restoran-putera-ayu-kampung-padang-jawa",
        "https://www.zomato.com/tr/hampton-roads/zaxbys-1-chesapeake",
        "https://www.zomato.com/selangor/the-fish-bowl-bandar-sunway",
        "https://www.zomato.com/selangor/country-barn-usj",
        "https://www.zomato.com/selangor/spice-road-usj",
        "https://www.zomato.com/pl/selangor/pei-fook-special-wantan-mee-kota-damansara",
        "https://www.zomato.com/id/selangor/restoran-baixian-taman-melawati",
        "https://www.zomato.com/pt/kuala-lumpur/bonjour-garden-bakery-cafe-off-jalan-ampang",
        "https://www.zomato.com/pt/selangor/baskin-robbins-seksyen-14-petaling-jaya",
        "https://www.zomato.com/id/selangor/rumah-kueh-dataran-sunway",
        "https://www.zomato.com/id/selangor/i-am-80s-ss-5",
        "https://www.zomato.com/sk/selangor/doi-chaang-coffee-seksyen-13-petaling-jaya",
        "https://www.zomato.com/selangor/metal-box-restaurant-cafe-damansara-perdana",
        "https://www.zomato.com/pt/selangor/cafe-9-taste-of-thai-seksyen-17-petaling-jaya",
        "https://www.zomato.com/id/selangor/mohamed-seksyen-17",
        "https://www.zomato.com/id/kuala-lumpur/strato-the-troika-kuala-lumpur-city-center",
        "https://www.zomato.com/sk/kuala-lumpur/al-azhar-restaurant-kebab-imbi",
        "https://www.zomato.com/it/selangor/meteora-usj",
        "https://www.zomato.com/it/selangor/graziea-food-station-damansara-damai",
        "https://www.zomato.com/id/selangor/oldschool-bandar-puteri"
    };

    public static String[] tripAdvisorSites = {
            "https://www.tripadvisor.com.sg/Restaurant_Review-g294265-d796940-Reviews-Summer_Pavilion-Singapore.html",
            "https://www.tripadvisor.com/Restaurant_Review-g60763-d10059872-Reviews-Fluffy_s_Cafe_Pizzeria-New_York_City_New_York.html",
            "https://www.tripadvisor.com.sg/Restaurant_Review-g294265-d10041900-Reviews-Sanobar_Lebanese_Cuisine_Seafood_Restaurant-Singapore.html",
            "https://www.tripadvisor.com/Restaurant_Review-g188590-d8562698-Reviews-Omelegg_City_Centre-Amsterdam_North_Holland_Province.html",
            "https://www.tripadvisor.com/Restaurant_Review-g308272-d1015824-Reviews-Jia_Jia_Tang_Bao_Huanghe_Road-Shanghai.html",
            "https://www.tripadvisor.com/Restaurant_Review-g294201-d2165744-Reviews-Shogun_Japanese_Restaurant-Cairo_Cairo_Governorate.html",
            "https://www.tripadvisor.com/Restaurant_Review-g60763-d1724974-Reviews-The_Black_Shack_Burgers-New_York_City_New_York.html",
            "https://www.tripadvisor.com/Restaurant_Review-g186338-d720761-Reviews-The_Ledbury-London_England.html",
            "https://www.tripadvisor.com/Restaurant_Review-g186338-d2412258-Reviews-Monmouth_Coffee_The_Borough-London_England.html",
            "https://www.tripadvisor.com/Restaurant_Review-g186525-d1520621-Reviews-Oink-Edinburgh_Scotland.html",
            "https://www.tripadvisor.com/Restaurant_Review-g41850-d532429-Reviews-Once_Upon_a_Table-Stockbridge_Massachusetts.html",
    };

    public static String[] hgwSites = {
            "http://www.hungrygowhere.com/cuisine/chinese",
            "http://www.hungrygowhere.com/cuisine/western",
            "http://www.hungrygowhere.com/cuisine/halal",
//            "http://www.hungrygowhere.com/cuisine/chinese?page_number=1",
//            "http://www.hungrygowhere.com/cuisine/chinese?page_number=2",
//            "http://www.hungrygowhere.com/cuisine/chinese?page_number=3",
//            "http://www.hungrygowhere.com/cuisine/western?page_number=1",
//            "http://www.hungrygowhere.com/cuisine/western?page_number=2",
//            "http://www.hungrygowhere.com/cuisine/western?page_number=3",
//            "http://www.hungrygowhere.com/cuisine/halal?page_number=1",
//            "http://www.hungrygowhere.com/cuisine/halal?page_number=2",
//            "http://www.hungrygowhere.com/cuisine/halal?page_number=3",
    };
//
//    private static final String HUNGRYGOWHERE_CHINESE_SEARCH_URL = "http://www.hungrygowhere.com/cuisine/chinese/";
//    private static final String HUNGRYGOWHERE_WESTERN_SEARCH_URL = "http://www.hungrygowhere.com/cuisine/western/";
//    private static final String HUNGRYGOWHERE_HALAL_SEARCH_URL = "http://www.hungrygowhere.com/cuisine/halal/";

    public static Thread[] threads;

    public static void main(String[] args) throws IOException {
        int threadsPerSite = 20;
        threads = new Thread[threadsPerSite*5];

        // Add Zomato crawlers
        int i=0;
        for (i=0; i<threadsPerSite; i++) {
            Thread newThread = new Thread(new ZomatoCrawler(zomatoInitialSites[i], i));
            newThread.start();
            threads[i] = newThread;
        }

        for (int j=0; i < threadsPerSite * 2; i++, j++) {
            Thread newThread = new Thread(new TripAdvisorCrawler(tripAdvisorSites[Math.min(j, tripAdvisorSites.length-1)], j));
            newThread.start();
            threads[i] = newThread;
        }

//        int hgwChineseRestaurantCount = HGWCrawler.getRestaurantCountHGW(HUNGRYGOWHERE_CHINESE_SEARCH_URL);
//        int hgwWesternRestaurantCount = HGWCrawler.getRestaurantCountHGW(HUNGRYGOWHERE_WESTERN_SEARCH_URL);
//        int hgwHalalRestaurantCount = HGWCrawler.getRestaurantCountHGW(HUNGRYGOWHERE_HALAL_SEARCH_URL);
//
//        int hgwChinesePageCount = hgwChineseRestaurantCount/6 + 1;
//        int hgwWesternPageCount = hgwWesternRestaurantCount/6 + 1;
//        int hgwHalalPageCount = hgwHalalRestaurantCount/6 + 1;

//        int hgwChinesePageCount = hgwChineseRestaurantCount/6 + 1;
//        int hgwWesternPageCount = hgwWesternRestaurantCount/6 + 1;
//        int hgwHalalPageCount = hgwHalalRestaurantCount/6 + 1;
//

        for (int k = 0; k < hgwSites.length; k++) {
            int hgwRestaurantCount = HGWCrawler.getRestaurantCountHGW(hgwSites[k]);
            int hgwPageCount = hgwRestaurantCount/6 + 1;
            int pagesPerThread = (int) Math.ceil(hgwPageCount / (double) threadsPerSite);
            System.out.println("HGW "+k+": "+hgwPageCount+", "+pagesPerThread);
            for (int j=0; i < threadsPerSite * 3; i++, j++) {
                int startPage = j * pagesPerThread;
                int endPage = j * pagesPerThread + 6;
                Thread newThread = new Thread(new HGWCrawler(hgwSites[k],
                        j, startPage, endPage));
                newThread.start();
                threads[i] = newThread;
            }
        }
        // Chinese restaurants
//        for (int j=0; i < threadsPerSite * 3; i++, j++) {
//            int startPage = j * hgwChinesePageCount;
//            int endPage = j * hgwChinesePageCount + 6;
//            Thread newThread = new Thread(new HGWCrawler(HUNGRYGOWHERE_CHINESE_SEARCH_URL,
//                    , j, ));
//            newThread.start();
//            threads[i] = newThread;
//        }

        // Crawl other sites here, e.g. below

//        for (int i=0; i<threads.length; i++) {
//            Thread newThread = new Thread(new ZomatoCrawler(zomatoInitialSites[0], i));
//            newThread.start();
//            threads[i] = newThread;
//        }

        for(int t = 0; t < threads.length; t++) {
            try {
                if (threads[t] != null) {
                    threads[t].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        CrawlerBase.saveDataToFile();
    }
}
