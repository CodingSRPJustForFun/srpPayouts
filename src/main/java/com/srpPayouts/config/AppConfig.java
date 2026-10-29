package com.srpPayouts.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/** Central place for paths and user-adjustable settings. */
public final class AppConfig {

    private static final Properties PROPERTIES = new Properties();

    private static final Path HOME = resolveHome();

    static {
        Path configFile = HOME.resolve("config.properties");

        if (Files.isRegularFile(configFile)) {
            try (InputStream input = Files.newInputStream(configFile)) {
                PROPERTIES.load(input);
            } catch (IOException ex) {
                throw new IllegalStateException(
                        "Could not read configuration file: " + configFile, ex);
            }
        }
    }

    private AppConfig() { }

    private static Path resolveHome() {
        String configuredHome = System.getProperty("srp.home");
        if (configuredHome != null && !configuredHome.isBlank()) {
            return Paths.get(configuredHome).toAbsolutePath().normalize();
        }

        Path workingDirectory = Paths.get(".").toAbsolutePath().normalize();
        if (Files.isRegularFile(workingDirectory.resolve("config.properties"))) {
            return workingDirectory;
        }

        Path nestedProjectHome = workingDirectory.resolve("srpPayouts");
        if (Files.isRegularFile(nestedProjectHome.resolve("config.properties"))) {
            return nestedProjectHome;
        }

        return workingDirectory;
    }

    public static Path home() {
        return HOME;
    }

    public static Path dataDir() {
        return resolvePath("data.dir", "data");
    }

    public static Path outputDir() {
        return resolvePath("output.dir", "output");
    }

    public static Path browserProfileDir() {
        return resolvePath("browser.profile.dir", "ChromeProfile");
    }

    public static Path typeNamesCsv() {
        return dataDir().resolve("invTypes.csv");
    }

    public static Path doctrineCsv() {
        return outputDir().resolve("DoctrineObjects.csv");
    }

    public static Path priceCrossCheckCsv() {
        return outputDir().resolve("FitObjects - Copy.csv");
    }

    public static Path fitObjectsCsv() {
        return outputDir().resolve("FitObjects.csv");
    }

    public static boolean useSavedDoctrineFile() {
        return Boolean.parseBoolean(
                PROPERTIES.getProperty(
                        "use.saved.doctrine.file",
                        "false")
                        .trim());
    }

    public static boolean useSavedJaniceUrls() {
        return Boolean.parseBoolean(
                PROPERTIES.getProperty(
                        "use.saved.janice.urls",
                        "false")
                        .trim());
    }

    public static int debugPort() {
        try {
            return Integer.parseInt(
                    PROPERTIES.getProperty(
                            "chrome.debug.port",
                            "9222")
                            .trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "chrome.debug.port must be a number",
                    ex);
        }
    }

    public static String chromeWindowPosition() {
        return PROPERTIES.getProperty("chrome.window.position", "1230,2").trim();
    }

    public static String chromeWindowSize() {
        return PROPERTIES.getProperty("chrome.window.size", "1330,1390").trim();
    }

    public static String chromePath() {
        return PROPERTIES.getProperty("chrome.path", "").trim();
    }

    public static String janiceHullUrl() {
        return PROPERTIES.getProperty("janice.hull.url", "").trim();
    }

    public static String janiceModulesUrl() {
        return PROPERTIES.getProperty("janice.modules.url", "").trim();
    }

    public static Path resolvePath(String key, String defaultValue) {
        String configured =
                PROPERTIES.getProperty(key, defaultValue).trim();

        Path path = Paths.get(configured);

        return (path.isAbsolute()
                ? path
                : HOME.resolve(path))
                .normalize();
    }

    public static void createWorkingDirectories() throws IOException {
        Files.createDirectories(dataDir());
        Files.createDirectories(outputDir());
        Files.createDirectories(browserProfileDir());
    }
}