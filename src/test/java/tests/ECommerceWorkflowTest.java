package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.ReportUtil;
import utils.ScreenshotUtil;
import utils.TestDataReader;

import java.util.*;

public class ECommerceWorkflowTest {

    private WebDriver driver;
    private HomePage homepage;
    private CategoryPage categoryPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private RegistrationPage registrationPage;
    private Map<String, String> testData;
    private final List<String> selectedProductNames = new ArrayList<>();
    private final List<String> selectedProductPrices = new ArrayList<>();

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Initialize page objects
        homepage = new HomePage(driver);
        categoryPage = new CategoryPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        registrationPage = new RegistrationPage(driver);

        driver.get("https://automationteststore.com/");

        //  Fix: use readTestData() with testCaseId
        testData = TestDataReader.readTestData("TC_01");
    }

    @Test(priority = 1)
    public void homepageCategoryVerification() {
        List<String> categories = homepage.getMainCategories();  //  Ensure method exists
        boolean validCategoryFound = false;

        for (String category : categories) {
            homepage.selectCategory(category);  //  Ensure method exists
            List<String> products = categoryPage.getAllProductNames();

            ReportUtil.log(" Category: " + category + " | Products Found: " + products.size());

            if (products.size() >= 3) {
                validCategoryFound = true;
                Assert.assertTrue(products.size() >= 3, "Category should have at least 3 products");
                ReportUtil.log(" Verified category '" + category + "' with sufficient products.");
                break;
            }
        }

        if (!validCategoryFound) {
            Assert.fail(" No category has at least 3 products to test");
        }
    }

    @Test(priority = 2, dependsOnMethods = "homepageCategoryVerification")
    public void addProductsToCart() {
        List<String> products = categoryPage.getAllProductNames();
        int count = 2;

        for (int i = 0; i < count && i < products.size(); i++) {
            categoryPage.selectProductByIndex(i);
            String name = productPage.getProductName();
            String price = productPage.getProductPrice();

            selectedProductNames.add(name);
            selectedProductPrices.add(price);

            productPage.addToCart();
            homepage.goHome();  //  Ensure this exists
            homepage.selectSameCategoryAgain();  // Ensure this exists
        }

        Assert.assertTrue(cartPage.getCartCount() >= 2, "At least 2 products should be in cart");
    }

    @Test(priority = 3, dependsOnMethods = "addProductsToCart")
    public void checkoutWorkflow() {
        cartPage.verifyCartContentsAndTotal(selectedProductNames, selectedProductPrices);
        cartPage.proceedToCheckout();

        registrationPage.fillForm(testData, false);
        registrationPage.submit();

        Assert.assertFalse(registrationPage.isErrorDisplayed(), "No registration error expected");
    }

    @AfterClass
    public void teardown() {
        ScreenshotUtil.capture(driver, "final_screenshot.png");
        ReportUtil.generateReport("report.txt");
        driver.quit();
    }
}
