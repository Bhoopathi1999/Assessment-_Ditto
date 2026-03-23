package base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;


public class BaseTest implements ITestListener {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {
        logger.info("========== TEST SUITE STARTED ==========");
        ExtentReportManager.getInstance();
    }

    @BeforeMethod
    public void setUp() {
        logger.info("--- Setting up test ---");
        driver = DriverFactory.initDriver();
        String baseUrl = ConfigReader.getInstance().getBaseUrl();
        logger.info("Navigating to: {}", baseUrl);
        driver.get(baseUrl);
        logger.info("Browser launched and navigated to application");
    }

    @AfterMethod
    public void tearDown() {
        logger.info("--- Tearing down test ---");
        DriverFactory.quitDriver();
        logger.info("Browser closed successfully");
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportManager.flush();
        logger.info("========== TEST SUITE COMPLETED ==========");
    }


    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        logger.info("TEST STARTED: {}", testName);
        ExtentReportManager.createTest(testName,
                description != null ? description : "Premium Validation Test");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.info("TEST PASSED: {}", testName);
        ExtentReportManager.getTest().log(Status.PASS,
                "Test '" + testName + "' PASSED successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.error("TEST FAILED: {}", testName);
        logger.error("Failure reason: {}", result.getThrowable().getMessage());

        // Capture screenshot on failure
        WebDriver currentDriver = DriverFactory.getDriver();
        if (currentDriver != null) {
            String screenshotPath = ScreenshotUtils.captureScreenshot(currentDriver, testName);
            if (screenshotPath != null) {
                ExtentReportManager.getTest().fail("Test failed - Screenshot attached",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
        }

        ExtentReportManager.getTest().log(Status.FAIL,
                "Test '" + testName + "' FAILED: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.warn("TEST SKIPPED: {}", testName);
        ExtentReportManager.getTest().log(Status.SKIP,
                "Test '" + testName + "' was SKIPPED: " + result.getThrowable().getMessage());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test context started: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test context finished: {}", context.getName());
    }
}
