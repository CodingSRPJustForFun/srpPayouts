package com.srpPayouts.scraping;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebScraperMultithreaded {

    // Callable class that defines the web scraping task
    static class ScraperTask implements Callable<String> {
        private final String url;

        ScraperTask(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            // Implement the scraping logic here
            // For example, make an HTTP request to the URL and process the response
            // This is a placeholder for the actual web scraping logic
            Document doc = Jsoup.connect(java.util.Objects.requireNonNull(url)).get();
            return doc.toString();
        }
    }

    public static ArrayList<String> doAllDoctrineScrape(List<String> urlsToScrape) throws InterruptedException, ExecutionException {

        // Create a thread pool with a fixed number of threads
        int numberOfThreads = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        // List to hold future results
        List<Future<String>> futures = new ArrayList<>();

        // Submit scraping tasks to the executor service
        for (String url : urlsToScrape) {
            ScraperTask task = new ScraperTask(url);
            Future<String> future = executorService.submit(task);
            futures.add(future);
        }
        ArrayList<String> allDoctrinePages = new ArrayList<>();

        // Process the results of the scraping tasks
        for (Future<String> future : futures) {
            // This blocks until the future's task is complete
            String result = future.get();
            allDoctrinePages.add(result);
        }

        // Shutdown the executor service
        executorService.shutdown();

        return allDoctrinePages;
    }
}