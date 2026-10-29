package com.srpPayouts.app;

import com.srpPayouts.config.AppConfig;
import com.srpPayouts.csv.CSVCode;
import com.srpPayouts.domain.FitObject;
import com.srpPayouts.domain.TBLDS_DiscordObject;
import com.srpPayouts.ui.MsgBox;
import com.srpPayouts.webpages.*;
import com.sun.jna.platform.FileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.time.Duration;

public class RunScrape {

    WebDriver driver;
    ChromeDriverService driverService;
    ChromeOptions options = new ChromeOptions();
    Process process = null;

    boolean useSavedJaniceURLs = AppConfig.useSavedJaniceUrls();
    boolean useSavedDoctrineFile = AppConfig.useSavedDoctrineFile(); // Use cached alliance fitting data when true.

    public void setup() throws IOException {
        AppConfig.createWorkingDirectories();
        System.out.println("Working folder: " + AppConfig.home());
        prepareRunFiles();
        setupDriver();
    }

    private void prepareRunFiles() throws IOException {
        if (!Files.isRegularFile(AppConfig.typeNamesCsv())) {
            throw new IOException("Required input file is missing: " + AppConfig.typeNamesCsv()
                    + ". Place the trusted invTypes.csv file in the data folder before running.");
        }

        System.out.println("Tidying up files....");
        File doctrineExistingFile = AppConfig.doctrineCsv().toFile();
        FileUtils fileUtils = FileUtils.getInstance();
        if (doctrineExistingFile.exists() && doctrineExistingFile.isFile() && !useSavedDoctrineFile) {
            if (fileUtils.hasTrash()) {
                try {
                    fileUtils.moveToTrash(new File[] { doctrineExistingFile });
                } catch (IOException e) {
                    e.printStackTrace();
                    doctrineExistingFile.delete();
                }
            } else {
                doctrineExistingFile.delete();
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
        }

        File crossCheckFile = AppConfig.priceCrossCheckCsv().toFile();
        File fitObjects = AppConfig.fitObjectsCsv().toFile();

        if (crossCheckFile.exists() && crossCheckFile.isFile() && fitObjects.exists() && fitObjects.isFile()) {
            if (fileUtils.hasTrash()) {
                try {
                    fileUtils.moveToTrash(new File[] { crossCheckFile });
                } catch (IOException e) {
                    // e.printStackTrace();
                    crossCheckFile.delete();
                }
            } else {
                crossCheckFile.delete();
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
        }

        // Preserve the previous output before starting a new run. Never delete it if
        // the cross-check copy cannot be created successfully.
        if (fitObjects.isFile()) {
            Files.copy(fitObjects.toPath(), crossCheckFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (!Files.isRegularFile(crossCheckFile.toPath()) || Files.size(crossCheckFile.toPath()) == 0) {
                throw new IOException("The previous payout CSV was not backed up correctly: " + crossCheckFile);
            }
            Files.delete(fitObjects.toPath());
            System.out.println("Previous payout CSV backed up to: " + crossCheckFile);
        }
    }

    public static void main(String[] args) {
        RunScrape application = new RunScrape();
        int exitCode = 0;
        try {
            application.setup();
            application.runScrape();
            System.out.println("SRP payout run finished. Check the output folder: " + AppConfig.outputDir());
        } catch (Throwable ex) {
            exitCode = 1;
            System.err.println("SRP payout run failed: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            application.close();
        }

        System.exit(exitCode);
    }

    private Path locateChrome() throws IOException {
        String configured = AppConfig.chromePath();
        if (!configured.isEmpty()) {
            Path path = Path.of(configured);
            if (Files.isRegularFile(path))
                return path;
            throw new IOException("Configured Chrome executable does not exist: " + path);
        }

        String programFiles = System.getenv("ProgramFiles");
        String programFilesX86 = System.getenv("ProgramFiles(x86)");
        String localAppData = System.getenv("LOCALAPPDATA");
        Path[] candidates = new Path[] {
                programFiles == null ? null : Path.of(programFiles, "Google", "Chrome", "Application", "chrome.exe"),
                programFilesX86 == null ? null
                        : Path.of(programFilesX86, "Google", "Chrome", "Application", "chrome.exe"),
                localAppData == null ? null : Path.of(localAppData, "Google", "Chrome", "Application", "chrome.exe")
        };

        for (Path candidate : candidates) {
            if (candidate != null && Files.isRegularFile(candidate))
                return candidate;
        }
        throw new IOException(
                "Chrome was not found in the usual Windows install locations. Set chrome.path in config.");
    }

    private void setupDriver() {
        try {
            Path chrome = locateChrome();
            int debugPort = AppConfig.debugPort();
            process = new ProcessBuilder(chrome.toString(), "--remote-debugging-port=" + debugPort,
                    "--user-data-dir=" + AppConfig.browserProfileDir(),
                    "--window-position=" + AppConfig.chromeWindowPosition(),
                    "--window-size=" + AppConfig.chromeWindowSize(), "--force-dark-mode").start();
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not start Chrome. Check chrome.path in config.properties or install Chrome.");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // e.printStackTrace();
        }

        // Setup for running remotely in Chrome
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        options.setExperimentalOption("debuggerAddress", "localhost:" + AppConfig.debugPort());
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--force-dark-mode");
        options.addArguments("--enable-features=WebContentsForceDark");
        // options.addArguments("--incognito");
        WebDriverManager.chromedriver().setup();
        driverService = ChromeDriverService.createDefaultService();
        driver = new ChromeDriver(java.util.Objects.requireNonNull(driverService), java.util.Objects.requireNonNull(options));
        driver.manage().timeouts().pageLoadTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(20)));
    }

    private void stopDriver() throws IOException {
        close();
        process.destroy();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // e.printStackTrace();
        }
    }

    public void runScrape() throws Throwable {
        ArrayList<FitObject> priceCheckFitObjects = new ArrayList<>();
        priceCheckFitObjects = CSVCode.getAllFitObjectsFromCrossCheckFile(
                priceCheckFitObjects, AppConfig.priceCrossCheckCsv().toString());

        ArrayList<TBLDS_DiscordObject> allCapitalHullPrices = collectCapitalHullPrices();
        ArrayList<FitObject> allFitObjects = collectFitObjects();
        String allShipTypesString = FittingsPage.getAllShipTypesString(allFitObjects);
        String allModulesString = BuyAllPage.getAllModulesString(allFitObjects);

        driver.manage().timeouts().pageLoadTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(30)));
        if (useSavedJaniceURLs
                && (AppConfig.janiceHullUrl().isBlank() || AppConfig.janiceModulesUrl().isBlank())) {
            throw new IllegalStateException(
                    "Saved Janice URLs are enabled but janice.hull.url and/or janice.modules.url are blank.");
        }

        JaniceResultsPage hullPrices = retrieveJaniceResults(
                "hull prices", allShipTypesString, AppConfig.janiceHullUrl());
        hullPrices.assignHullPrices(allFitObjects);
        hullPrices.assignCapitalHullPrices(allFitObjects, allCapitalHullPrices);

        JaniceResultsPage modulePrices = retrieveJaniceResults(
                "module prices", allModulesString, AppConfig.janiceModulesUrl());
        calculateAndWritePayouts(allFitObjects, priceCheckFitObjects, modulePrices);
    }

    private ArrayList<TBLDS_DiscordObject> collectCapitalHullPrices() throws Throwable {
        ArrayList<TBLDS_DiscordObject> capitalHullPrices = new ArrayList<>();
        TBLDS_DiscordPage discordPage = new TBLDS_DiscordPage(driver);
        MsgBox.info("Click OK when Discord is open");
        discordPage.waitPageLoaded();
        MsgBox.info("If the Capital prices aren't visible\nPress Page Up 2 or 3 times.\nThen click OK");
        discordPage.getCapitalPrices(capitalHullPrices);
        return capitalHullPrices;
    }

    private ArrayList<FitObject> collectFitObjects() throws Throwable {
        ArrayList<FitObject> fitObjects = new ArrayList<>();
        if (useSavedDoctrineFile) {
            return CSVCode.getAllDoctrineObjects(fitObjects, AppConfig.doctrineCsv().toString());
        }

        FittingsHomePage fittingsHomePage = new FittingsHomePage(driver);
        MsgBox.info("Is the page logged in?");
        fittingsHomePage.waitPageLoaded();
        fittingsHomePage.set100Entries();

        int fittingsPageNumber = 0;
        while (fittingsPageNumber < 100) {
            FittingsPage fittingsPage = new FittingsPage(driver);
            fittingsPage.waitPageLoaded();
            fitObjects = fittingsPage.captureAllFittings(fitObjects);
            if (!fittingsPage.findNextButton())
                break;
            fittingsPage.clickNextButton();
        }
        if (fittingsPageNumber > 100) {
            throw new IllegalStateException(
                    "Stopped after 100 fitting-list pages. Check whether the website's Next button works.");
        }
        fitObjects = fittingsHomePage.addExtraShips(fitObjects);
        return scrapeDoctrineDetails(fitObjects);
    }

    private ArrayList<FitObject> scrapeDoctrineDetails(ArrayList<FitObject> fitObjects) throws Throwable {
        int completed = 0;
        FitObject.sortByDoctrineName(fitObjects);
        for (FitObject fitObject : fitObjects) {
            if (fitObject.getFittingURL().length() > 0) {
                scrapeDoctrineDetails(fitObject);
            }
            if (completed++ > 5000)
                break;
            if (Math.floorMod(completed, 5) == 0) {
                System.out.println("Done " + completed + "/" + fitObjects.size() + " - "
                        + (completed * 100) / fitObjects.size() + "%");
            }
        }

        CSVCode.writeDoctrineToCSV(fitObjects);
        return fitObjects;
    }

    private void scrapeDoctrineDetails(FitObject fitObject) throws Throwable {
        boolean detailsLoaded = false;
        int readAttempts = 0;
        while (!detailsLoaded && readAttempts++ < 3) {
            BuyAllPage buyAllPage = null;
            boolean pageOpened = false;
            int openAttempts = 0;
            while (!pageOpened && openAttempts++ < 3) {
                try {
                    buyAllPage = new BuyAllPage(driver, fitObject.getFittingURL());
                    pageOpened = true;
                } catch (Exception ex) {
                    System.err.println(
                            "Could not open fitting (attempt " + openAttempts + "/3): " + fitObject.getFittingURL());
                    stopDriver();
                    setupDriver();
                }
            }
            if (!pageOpened || buyAllPage == null)
                break;
            try {
                buyAllPage.waitPageLoaded();
                buyAllPage.getAllModules(fitObject);
                buyAllPage.getLastUpdated(fitObject);
                // buyAllPage.getNoDoctrineFits(fitObject);
                detailsLoaded = true;
            } catch (Exception ex) {
                System.err.println(
                        "Could not read fitting (attempt " + readAttempts + "/3): " + fitObject.getFittingURL());
                if (readAttempts < 3) {
                    stopDriver();
                    setupDriver();
                }
            }
        }
        if (!detailsLoaded) {
            throw new IllegalStateException("Failed to read fitting after 3 attempts: " + fitObject.getFittingURL());
        }
    }

    private JaniceResultsPage retrieveJaniceResults(String priceType, String itemNames, String savedUrl)
            throws Throwable {
        if (useSavedJaniceURLs) {
            System.out.println("Using configured saved Janice " + priceType + " URL.");
            JaniceResultsPage resultsPage = new JaniceResultsPage(driver, savedUrl);
            resultsPage.waitPageLoaded();
            return resultsPage;
        }
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                JaniceHomePage homePage = new JaniceHomePage(driver);
                homePage.waitPageLoaded();
                homePage.pasteList(itemNames);
                homePage.clickSubmit();
                JaniceResultsPage resultsPage = new JaniceResultsPage(driver, "");
                resultsPage.waitPageLoaded();
                return resultsPage;
            } catch (Exception ex) {
                System.err.println(
                        "Could not retrieve Janice " + priceType + " (attempt " + attempt + "/3): " + ex.getMessage());
                if (attempt < 3) {
                    stopDriver();
                    setupDriver();
                }
            }
        }
        throw new IllegalStateException("Could not retrieve Janice " + priceType + " after 3 attempts.");
    }

    private void calculateAndWritePayouts(ArrayList<FitObject> fitObjects,
            ArrayList<FitObject> priceCheckFitObjects,
            JaniceResultsPage modulePrices) throws Throwable {

        ArrayList<FitObject> fixedFitObjects = FitObject.addFixedPriceItems(new ArrayList<>());

        fitObjects = modulePrices.assignModulePrices(fitObjects);
        fixedFitObjects = modulePrices.assignModulePrices(fixedFitObjects);

        fitObjects = FitObject.calculateTotalModuleCost(fitObjects);
        fitObjects = CSVCode.assignTypeId(
                fitObjects,
                AppConfig.typeNamesCsv().toString());

        fitObjects = InsurancePage.getInsuranceValues(fitObjects);
        fitObjects = FitObject.calculatePayouts(fitObjects);

        FitObject.checkForMissingModules(
                fitObjects,
                AppConfig.typeNamesCsv().toString());

        FitObject.crossCheckPrices(
                fitObjects,
                priceCheckFitObjects);

        FitObject.sortByShipType(fitObjects);

        fixedFitObjects = FitObject.calculateTotalModuleCost(fixedFitObjects);
        fixedFitObjects = FitObject.calculatePayouts(fixedFitObjects);

        FitObject.checkForMissingModules(
                fixedFitObjects,
                AppConfig.typeNamesCsv().toString());

        FitObject.crossCheckFixedPrices(
                fixedFitObjects,
                priceCheckFitObjects);

        CSVCode.WriteDataCSV(fitObjects, fixedFitObjects);
    }

    public void close() {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        } catch (Exception ex) {
            System.err.println("Warning: could not close WebDriver cleanly: " + ex.getMessage());
        } finally {
            if (driver != null)
                driver = null;
        }
        try {
            if (driverService != null) {
                driverService.stop();
                driverService = null;
            }
        } catch (Exception ex) {
            System.err.println("Warning: could not stop ChromeDriver service cleanly: " + ex.getMessage());
        } finally {
            driverService = null;
        }
        try {
            if (process != null && process.isAlive())
                process.destroy();
        } catch (Exception ex) {
            System.err.println("Warning: could not close Chrome process cleanly: " + ex.getMessage());
        }
    }
}