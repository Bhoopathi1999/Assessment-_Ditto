package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(fis);
            logger.info("Configuration properties loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load config.properties: {}", e.getMessage());
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl", "https://app.joinditto.in/fq");
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait", "0"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicitWait", "10"));
    }

    public String getReportPath() {
        return properties.getProperty("reportPath", "./reports/TestReport.html");
    }

    public String getScreenshotPath() {
        return properties.getProperty("screenshotPath", "./screenshots/");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
