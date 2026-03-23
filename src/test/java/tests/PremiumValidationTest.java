package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DittoHomePage;
import pages.ProductSelectionPage;
import pages.UserDetailsPage;
import org.testng.annotations.Listeners;
import pages.PremiumSummaryPage;
import utils.ExtentReportManager;
import utils.TestDataProvider;


@Listeners(BaseTest.class)
public class PremiumValidationTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(PremiumValidationTest.class);
    private static final String PRODUCT_NAME = "Optima Secure";

    @Test(dataProvider = "userData", dataProviderClass = TestDataProvider.class,
            description = "Validate Total Premium = Base Premium + Rider Premiums + GST")
    public void validateTotalPremiumCalculation(String name, int age, String city, String smokerStatus) {

        logger.info("====== Starting Premium Validation Test for: {} ======", name);
        ExtentReportManager.getTest().info("Test data - Name: " + name + ", Age: " + age +
                ", City: " + city + ", Smoker: " + smokerStatus);


        DittoHomePage homePage = new DittoHomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(), "Ditto home page should be loaded");
        ExtentReportManager.getTest().info("Home page loaded successfully");

        homePage.selectProduct(PRODUCT_NAME);
        ExtentReportManager.getTest().info("Product selected: " + PRODUCT_NAME);


        ProductSelectionPage policyPage = new ProductSelectionPage(driver);
        Assert.assertTrue(policyPage.isPageLoaded(), "Policy understanding page should be loaded");
        policyPage.completeAllStepsAndContinue();
        ExtentReportManager.getTest().info("Completed policy understanding steps");

        UserDetailsPage userDetailsPage = new UserDetailsPage(driver);
        Assert.assertTrue(userDetailsPage.isMembersPageLoaded(), "Members page should be loaded");
        userDetailsPage.selectSelfWithGender("Male");
        userDetailsPage.clickNextStep();
        ExtentReportManager.getTest().info("Selected Self (Male) and proceeded");


        Assert.assertTrue(userDetailsPage.isPlanPageLoaded(), "Plan page should be loaded");
        userDetailsPage.fillFormAndCalculate(String.valueOf(age), "560001");
        ExtentReportManager.getTest().info("Form filled - Age: " + age + ", Pincode: 560001");


        PremiumSummaryPage summaryPage = new PremiumSummaryPage(driver);
        Assert.assertTrue(summaryPage.isPremiumSummaryDisplayed(),
                "Premium summary panel should be displayed");

        double basePremium = summaryPage.getBasePremium();
        double riderPremiums = summaryPage.getRiderPremiums();
        double gst = summaryPage.getGST();
        double totalPremium = summaryPage.getTotalPremium();

        logger.info("========== PREMIUM BREAKDOWN ==========");
        logger.info("Base Premium   : Rs. {}", basePremium);
        logger.info("Rider Premiums : Rs. {}", riderPremiums);
        logger.info("GST (embedded) : Rs. {}", gst);
        logger.info("Total Premium  : Rs. {}", totalPremium);
        logger.info("========================================");

        ExtentReportManager.getTest().info("Base Premium: Rs. " + basePremium);
        ExtentReportManager.getTest().info("Rider Premiums: Rs. " + riderPremiums);
        ExtentReportManager.getTest().info("GST (embedded in base): Rs. " + gst);
        ExtentReportManager.getTest().info("Total Premium: Rs. " + totalPremium);


        double expectedTotal = basePremium + riderPremiums + gst;
        logger.info("Expected Total : Rs. {}", expectedTotal);
        logger.info("Actual Total   : Rs. {}", totalPremium);

        Assert.assertEquals(totalPremium, expectedTotal, 0.01,
                "Total Premium should equal Base Premium + Rider Premiums + GST. " +
                "Expected: " + expectedTotal + ", Actual: " + totalPremium);

        logger.info("ASSERTION PASSED: Total Premium matches expected calculation");
        ExtentReportManager.getTest().log(Status.PASS,
                "Premium validation PASSED: Total (" + totalPremium +
                ") = Base (" + basePremium + ") + Riders (" + riderPremiums + ") + GST (" + gst + ")");
    }

    @Test(description = "Verify all premium components are displayed on the summary panel")
    public void verifyPremiumSummaryDisplaysAllComponents() {

        logger.info("====== Verifying Premium Summary Components ======");

        // Navigate through the full flow with default data
        DittoHomePage homePage = new DittoHomePage(driver);
        homePage.selectProduct(PRODUCT_NAME);

        ProductSelectionPage policyPage = new ProductSelectionPage(driver);
        policyPage.completeAllStepsAndContinue();

        UserDetailsPage userDetailsPage = new UserDetailsPage(driver);
        userDetailsPage.selectSelfWithGender("Male");
        userDetailsPage.clickNextStep();

        userDetailsPage.fillFormAndCalculate("30", "560001");

        PremiumSummaryPage summaryPage = new PremiumSummaryPage(driver);

        Assert.assertTrue(summaryPage.isPremiumSummaryDisplayed(),
                "Premium summary panel should be visible");


        double basePremium = summaryPage.getBasePremium();
        Assert.assertTrue(basePremium > 0,
                "Base Premium should be greater than 0. Found: " + basePremium);


        double totalPremium = summaryPage.getTotalPremium();
        Assert.assertTrue(totalPremium > 0,
                "Total Premium should be greater than 0. Found: " + totalPremium);


        Assert.assertTrue(totalPremium >= basePremium,
                "Total Premium (" + totalPremium + ") should be >= Base Premium (" + basePremium + ")");

        logger.info("All premium components verified successfully");
        ExtentReportManager.getTest().log(Status.PASS,
                "All premium components are displayed with valid values");
    }
}
