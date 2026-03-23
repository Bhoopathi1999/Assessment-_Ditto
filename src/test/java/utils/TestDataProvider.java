package utils;

import org.testng.annotations.DataProvider;


public class TestDataProvider {


    @DataProvider(name = "userData")
    public static Object[][] getUserData() {
        return new Object[][]{
                {"John Doe", 28, "Bangalore", "No"},
                {"Alice Smith", 35, "Mumbai", "Yes"},
                {"Rahul Kumar", 45, "Delhi", "No"}
        };
    }


    @DataProvider(name = "singleUserData")
    public static Object[][] getSingleUserData() {
        return new Object[][]{
                {"Test User", 30, "Bangalore", "No"}
        };
    }
}
