package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;


public class PremiumSummaryPage {

    private static final Logger logger = LogManager.getLogger(PremiumSummaryPage.class);
    private final WebDriver driver;


    private final By basePremiumAmount = By.xpath(
            "//span[text()='Base Premium']/following-sibling::span[1]");
    private final By totalPremiumAmount = By.xpath(
            "//span[text()='Total Premium']/following-sibling::span[1]");


    private final By recommendedAddonsAmount = By.xpath(
            "//span[contains(text(),'Recommended Add-ons')]/following-sibling::span[1]");
    private final By otherAddonsAmount = By.xpath(
            "//span[contains(text(),'Other Add-ons')]/following-sibling::span[1]");

    private final By premiumSummaryIndicator = By.xpath(
            "//span[text()='Base Premium']");


    public PremiumSummaryPage(WebDriver driver) {
        this.driver = driver;
        logger.info("PremiumSummaryPage initialized");
    }


    public boolean isPremiumSummaryDisplayed() {
        try {
            WaitUtils.waitForElementVisible(driver, premiumSummaryIndicator, 20);
            logger.info("Premium summary panel is displayed");
            return true;
        } catch (Exception e) {
            logger.error("Premium summary not found: {}", e.getMessage());
            return false;
        }
    }


    public double getBasePremium() {
        logger.info("Extracting Base Premium");
        try {
            WebElement el = WaitUtils.waitForElementVisible(driver, basePremiumAmount, 15);
            double value = parseAmount(el.getText());
            logger.info("Base Premium: {}", value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to extract Base Premium: {}", e.getMessage());
            return extractAmountByLabel("Base Premium");
        }
    }


    public double getRiderPremiums() {
        logger.info("Extracting Rider/Add-on Premiums");
        double total = 0.0;

        total += extractAddOnAmount(recommendedAddonsAmount, "Recommended Add-ons");
        total += extractAddOnAmount(otherAddonsAmount, "Other Add-ons");

        logger.info("Total Rider/Add-on Premiums: {}", total);
        return total;
    }

    public double getGST() {
        logger.info("GST is embedded in Base Premium on Ditto - no separate line item");
        return 0.0;
    }


    public double getTotalPremium() {
        logger.info("Extracting Total Premium");
        try {
            WebElement el = WaitUtils.waitForElementVisible(driver, totalPremiumAmount, 15);
            double value = parseAmount(el.getText());
            logger.info("Total Premium: {}", value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to extract Total Premium: {}", e.getMessage());
            return extractAmountByLabel("Total Premium");
        }
    }


    private double parseAmount(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0.0;
        }
        String cleaned = text.replaceAll("[^0-9.]", "");
        if (cleaned.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse amount '{}': {}", text, e.getMessage());
            return 0.0;
        }
    }

    private double extractAddOnAmount(By locator, String label) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement el : elements) {
                String text = el.getText().trim();
                if (text.contains("₹") || text.matches(".*\\d+.*")) {
                    double val = parseAmount(text);
                    logger.info("{}: {}", label, val);
                    return val;
                }
            }
        } catch (Exception e) {
            logger.info("{} not found or zero: {}", label, e.getMessage());
        }
        return 0.0;
    }

    private double extractAmountByLabel(String label) {
        try {
            By locator = By.xpath("//span[text()='" + label + "']/following-sibling::span[1]");
            WebElement el = WaitUtils.waitForElementVisible(driver, locator, 10);
            double value = parseAmount(el.getText());
            logger.info("{} (fallback): {}", label, value);
            return value;
        } catch (Exception e) {
            logger.error("Fallback extraction failed for '{}': {}", label, e.getMessage());
            return 0.0;
        }
    }
}
