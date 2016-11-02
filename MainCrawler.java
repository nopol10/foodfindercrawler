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

    public static Thread[] threads;

    public static void main(String[] args) throws IOException {
        threads = new Thread[20];

        // Add Zomato crawlers
        for (int i=0; i<threads.length; i++) {
            Thread newThread = new Thread(new ZomatoCrawler(zomatoInitialSites[i], i));
            newThread.start();
            threads[i] = newThread;
        }

        // Crawl other sites here, e.g. below

//        for (int i=0; i<threads.length; i++) {
//            Thread newThread = new Thread(new ZomatoCrawler(zomatoInitialSites[0], i));
//            newThread.start();
//            threads[i] = newThread;
//        }

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
