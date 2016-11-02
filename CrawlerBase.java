import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


public abstract class CrawlerBase implements Runnable {

    protected static Object outputFileLock = new Object();
    protected static Object urlsToCrawlLock = new Object();
    protected static final String processedUrlFilename = "processed_urls.txt";
    protected static final String passToParserUrlFilename = "urls.txt";

    protected static HashSet<String> urlsToCrawl = new HashSet<>();
    protected static HashSet<String> crawledUrls = new HashSet<>();
    protected static HashSet<String> goodUrls = new HashSet<>();

    protected static long previousSaveTime = System.currentTimeMillis();

    protected static final int SAVE_INTERVAL = 60 * 1000;

    protected static boolean shouldStop = false;

    protected String initialSite;

    protected int index = -1;

    protected static int sitesFound = 0;

    public CrawlerBase(String initialSite) {
        this.initialSite = initialSite;
    }

    protected static boolean canUrlBeAdded(String url) {
        boolean canAdd = true;
//        synchronized (urlsToCrawlLock) {
//            if (crawledUrls.contains(url)) {
//                return false;
//            }
//        }
        if (true) {
            return true;
        }
        synchronized (outputFileLock) {
            // Was url processed before?
            Path processedFilePath = Paths.get(processedUrlFilename);
            try (BufferedReader reader = Files.newBufferedReader(processedFilePath)) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.equals(url)) {
                        canAdd = false;
                        break;
                    }
                }
                reader.close();
            } catch (IOException e) {
            }
            if (!canAdd) {
                return false;
            }

            Path passedFilePath = Paths.get(passToParserUrlFilename);
            try (BufferedReader reader = Files.newBufferedReader(passedFilePath)) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.equals(url)) {
                        canAdd = false;
                        break;
                    }
                }
                reader.close();
            } catch (IOException e) {
            }
        }
        return canAdd;
    }

    protected void insertUrlToMemory(String url) {
        synchronized (urlsToCrawlLock) {
            if (crawledUrls.contains(url) || urlsToCrawl.contains(url)) {
                return;
            }
            System.out.println("Inserting "+url);
            urlsToCrawl.add(url);
        }
    }

    protected String getOldestUrlToRead() {
        synchronized (urlsToCrawlLock) {
            String url = urlsToCrawl.iterator().next();
            urlsToCrawl.remove(url);
            crawledUrls.add(url);
            return url;
        }
    }

    protected static void updateProcessedUrlFile() {
        synchronized (outputFileLock) {
            synchronized (urlsToCrawlLock) {
                // Load existing url in processed file
                Path processedFilePath = Paths.get(processedUrlFilename);
                if (!processedFilePath.toFile().isFile()) {
                    try {
                        Files.createFile(processedFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Updating processed url file: "+processedFilePath.toAbsolutePath().toString());
                LinkedList<String> linesToAdd = new LinkedList<>();
                HashSet<String> linesInFile = new HashSet<>();
                try (BufferedReader reader = Files.newBufferedReader(processedFilePath)) {
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        linesInFile.add(line);
                    }
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Processed file path error:");
                    e.printStackTrace();
                }

                for (String crawledUrl : crawledUrls) {
                    if (!linesInFile.contains(crawledUrl)) {
                        // File doesn't have this url, add to file
                        linesToAdd.add(crawledUrl);
                    }
                }

                try (BufferedWriter writer = Files.newBufferedWriter(processedFilePath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                    for (String crawledUrl : linesToAdd) {
                        writer.write(crawledUrl);
                        writer.newLine();
                    }
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    // Remove anchors and params from url
    public static String cleanUrl(String url) {
        String out = url;
        String[] noParams = url.split("\\?");
        out = noParams[0];
        noParams = out.split("#");
        out = noParams[0];
        return out;
    }

    // Insert urls to be crawled into the urls.txt file
    public static void insertUrlsToFile() {
        // Check whether the url was processed before
        synchronized (outputFileLock) {
            synchronized (urlsToCrawlLock) {
                String[] urls = goodUrls.toArray(new String[goodUrls.size()]);
                Path passedFilePath = Paths.get(passToParserUrlFilename);
                System.out.println(urls.length + " good URLS to write");
                try (BufferedWriter writer = Files.newBufferedWriter(passedFilePath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                    for (String url : urls) {
                        boolean canAdd = canUrlBeAdded(url);
                        if (!canAdd) {
                            return;
                        }
                        writer.write(url);
                        writer.newLine();
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected static void saveDataToFile() {
        updateProcessedUrlFile();
        insertUrlsToFile();
    }


    protected Document getWebsiteData(String url) {
        Document doc = null;
        System.out.println(index + " trying " + url);
        try {
            doc = Jsoup.connect(url).timeout(0).userAgent("Mozilla/5.0").get();
        } catch (HttpStatusException hse) {
            System.out.println(index + " failed status " + url + " " + hse.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) {
            System.out.println(index + " failed for " + url + " " + iae.getMessage());
        }
        return doc;
    }

    /**
     *
     * @param document
     * @return Whether this website's url should be added to the list to be passed to the parser
     */
    protected boolean processWebsiteData(Document document) {
        return false;
    }

    protected boolean isValidUrl(String url) {
        if (url.length() <= 0) {
            return false;
        }
        return true;
    }

    protected void runMainLoop() {
        boolean justStarted = true;
        while (!shouldStop /*&& urlsToCrawl.size() >= 0*/) {
            // Crawl

            String crawlUrl = "";
            if (justStarted) {
                crawlUrl = initialSite;
                synchronized (urlsToCrawlLock) {
                    crawledUrls.add(crawlUrl);
                }
                justStarted = false;
            } else {
                crawlUrl = getOldestUrlToRead();
            }

            // Read the website and perform any processing (should override processWebsiteData in children)
            if (isValidUrl(crawlUrl)) {
                Document doc = getWebsiteData(crawlUrl);
                if (doc != null) {
                    if (processWebsiteData(doc) == true) {
                        goodUrls.add(cleanUrl(crawlUrl));
                    }
                }
            }

            // Temporary break for testing
            if (goodUrls.size() > 200) {
                shouldStop = true;
                System.out.println("Get out!!!");
            }

            long currentTime = System.currentTimeMillis();
            if (currentTime - previousSaveTime >= SAVE_INTERVAL) {
                previousSaveTime = currentTime;
                saveDataToFile();
            }
        }
//        saveDataToFile();
    }
}
