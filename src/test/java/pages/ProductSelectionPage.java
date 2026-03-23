package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;


public class ProductSelectionPage {

    private static final Logger logger = LogManager.getLogger(ProductSelectionPage.class);
    private final WebDriver driver;


    private final By pageHeading = By.xpath("//h1[contains(text(),'Understand your policy')]");
    private final By nextButton = By.xpath("//button[.//span[text()='Next']]");
    private final By continueButton = By.xpath("//button[.//span[text()='Continue']]");


    public ProductSelectionPage(WebDriver driver) {
        this.driver = driver;
        logger.info("ProductSelectionPage (Policy Understanding) initialized");
    }

    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForPageLoad(driver);
            WaitUtils.waitForElementPresence(driver, pageHeading, 15);
            logger.info("Policy understanding page loaded");
            return true;
        } catch (Exception e) {
            logger.error("Policy page not loaded: {}", e.getMessage());
            return false;
        }
    }


    public void completeAllStepsAndContinue() {
        logger.info("Navigating through all 4 policy understanding steps");
        WaitUtils.waitForPageLoad(driver);

        for (int step = 1; step <= 3; step++) {
            try {
                WebElement next = WaitUtils.waitForElementClickable(driver, nextButton, 10);
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", next);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", next);
                logger.info("Clicked Next for step {}", step);
                Thread.sleep(500);
            } catch (Exception e) {
                logger.info("Next button not found at step {} - may already be on Continue", step);
                break;
            }
        }
        try {
            WebElement cont = WaitUtils.waitForElementClickable(driver, continueButton, 10);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", cont);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cont);
            logger.info("Clicked Continue to proceed to members page");
        } catch (Exception e) {
           
            logger.warn("Continue button not found, trying fallback");
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            for (WebElement btn : buttons) {
                String text = btn.getText().trim();
                if (text.equals("Continue") || text.equals("Next")) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                    logger.info("Clicked fallback button: {}", text);
                    break;
                }
            }
        }


        logger.info("Waiting for navigation to members page");
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/members"));
        logger.info("Navigated to members page");
    }
}
