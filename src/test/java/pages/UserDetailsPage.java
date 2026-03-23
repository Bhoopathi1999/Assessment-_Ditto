package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

import java.time.Duration;

import java.util.List;

public class UserDetailsPage {

    private static final Logger logger = LogManager.getLogger(UserDetailsPage.class);
    private final WebDriver driver;


    private final By membersHeading = By.xpath("//h1[contains(text(),'Who all do you want to buy')]");
    private final By selfChipLabel = By.xpath(
            "//div[contains(@class,'mantine-Chip-root')]//label[.//span[text()='Self']]");
    private final By maleGenderSelf = By.xpath(
            "//label[.//span[text()='Self']]//div[contains(@class,'memberGender') and text()='Male']");
    private final By femaleGenderSelf = By.xpath(
            "//label[.//span[text()='Self']]//div[contains(@class,'memberGender') and text()='Female']");
    private final By nextStepButton = By.xpath(
            "//footer//button | //button[.//span[contains(text(),'Next Step') or contains(text(),'Next step')]]");


    private final By planHeading = By.xpath("//h1[contains(text(),'Tell us about you')]");
    private final By ageInput = By.cssSelector("input[placeholder='Your age']");
    private final By pincodeInput = By.cssSelector("input[placeholder='Enter your pin code']");
    private final By calculatePremiumButton = By.xpath(
            "//form//button[@type='submit']");


    public UserDetailsPage(WebDriver driver) {
        this.driver = driver;
        logger.info("UserDetailsPage initialized");
    }


    public boolean isMembersPageLoaded() {
        try {
            WaitUtils.waitForPageLoad(driver);
            WaitUtils.waitForElementPresence(driver, membersHeading, 15);
            logger.info("Members selection page loaded");
            return true;
        } catch (Exception e) {
            logger.error("Members page not loaded: {}", e.getMessage());
            return false;
        }
    }

    public void selectSelfWithGender(String gender) {
        logger.info("Selecting Self with gender: {}", gender);
        WaitUtils.waitForPageLoad(driver);

        WebElement selfLabel = WaitUtils.waitForElementClickable(driver, selfChipLabel, 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selfLabel);
        logger.info("Self chip label clicked");

        try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        By genderLocator = "Male".equalsIgnoreCase(gender) ? maleGenderSelf : femaleGenderSelf;
        try {
            WebElement genderEl = WaitUtils.waitForElementClickable(driver, genderLocator, 5);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderEl);
            logger.info("Gender '{}' selected for Self", gender);
        } catch (Exception e) {
            logger.warn("Direct gender click failed, trying broad fallback");
            List<WebElement> genderDivs = driver.findElements(
                    By.xpath("//div[contains(@class,'memberGender') and text()='" + gender + "']"));
            if (!genderDivs.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderDivs.get(0));
                logger.info("Gender '{}' selected via fallback", gender);
            } else {
                logger.error("Could not find gender selector for '{}'", gender);
            }
        }
    }


    public void clickNextStep() {
        logger.info("Clicking Next Step on members page");
        try {
            WebElement btn = WaitUtils.waitForElementClickable(driver, nextStepButton, 10);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception e) {
            logger.warn("Next Step button not found via locator, using fallback");
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            for (WebElement btn : buttons) {
                if (btn.getText().trim().contains("Next") && btn.findElements(By.xpath("ancestor::footer")).size() > 0) {
                    btn.click();
                    break;
                }
            }
        }
        logger.info("Waiting for navigation to plan page");
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/plan"));
        logger.info("Navigated to plan page");
    }


    public boolean isPlanPageLoaded() {
        try {
            WaitUtils.waitForPageLoad(driver);
            WaitUtils.waitForElementPresence(driver, planHeading, 15);
            logger.info("Plan page loaded");
            return true;
        } catch (Exception e) {
            logger.error("Plan page not loaded: {}", e.getMessage());
            return false;
        }
    }


    public void enterAge(String age) {
        logger.info("Entering age: {}", age);
        WebElement ageField = WaitUtils.waitForElementVisible(driver, ageInput, 10);
        setReactInputValue(ageField, age);
    }


    public void enterPincode(String pincode) {
        logger.info("Entering pincode: {}", pincode);
        WebElement pinField = WaitUtils.waitForElementVisible(driver, pincodeInput, 10);
        setReactInputValue(pinField, pincode);
    }


    public void clickCalculatePremium() {
        logger.info("Clicking Calculate Premium");
        WebElement calcBtn = WaitUtils.waitForElementClickable(driver, calculatePremiumButton, 10);
        calcBtn.click();
        logger.info("Calculate Premium clicked");
    }


    public void fillFormAndCalculate(String age, String pincode) {
        logger.info("Filling plan form - Age: {}, Pincode: {}", age, pincode);
        enterAge(age);
        enterPincode(pincode);
        clickCalculatePremium();
        logger.info("Form submitted, waiting for premium calculation");
    }


    private void setReactInputValue(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(" +
                "  window.HTMLInputElement.prototype, 'value').set;" +
                "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element, value
        );
    }
}
