package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import utils.WaitUtils;

import java.util.List;


public class DittoHomePage {

    private static final Logger logger = LogManager.getLogger(DittoHomePage.class);
    private final WebDriver driver;


    private final By productCards = By.cssSelector(".mantine-Paper-root");
    private final By pageLoadIndicator = By.cssSelector("main");


    public DittoHomePage(WebDriver driver) {
        this.driver = driver;
        logger.info("DittoHomePage initialized");
    }


    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForPageLoad(driver);
            WaitUtils.waitForElementPresence(driver, pageLoadIndicator);
            WaitUtils.waitForElementPresence(driver, productCards);
            logger.info("Ditto home page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Ditto home page failed to load: {}", e.getMessage());
            return false;
        }
    }


    public void selectProduct(String productName) {
        logger.info("Selecting product: {}", productName);
        WaitUtils.waitForPageLoad(driver);
        WaitUtils.waitForElementPresence(driver, productCards, 15);

        List<WebElement> cards = driver.findElements(productCards);
        for (WebElement card : cards) {
            String cardText = card.getText();
            if (cardText.contains(productName) && !cardText.contains(productName + " Super")) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", card);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", card);
                logger.info("Product '{}' selected successfully", productName);
                return;
            }
        }
        throw new RuntimeException("Product card not found: " + productName);
    }
}
