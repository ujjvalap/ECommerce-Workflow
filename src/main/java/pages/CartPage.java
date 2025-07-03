package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ReportUtil;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By cartIcon = By.cssSelector("a[href*='checkout/cart']");
    private final By cartTable = By.cssSelector("table.table"); // for presence
    private final By cartItems = By.cssSelector("table.table.table-striped tbody tr");
    private final By productName = By.cssSelector(".contentDescription > a");
    private final By productPrice = By.cssSelector(".cart_total .price");
    private final By checkoutBtn = By.id("cart_checkout1");

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Opens the cart and waits for the cart table or empty message.
     */
    public void openCart() {
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        icon.click();
        ReportUtil.log(" Navigated to cart page");

        // Wait for cart to load fully
        wait.until(ExpectedConditions.or(
            ExpectedConditions.visibilityOfElementLocated(cartTable),
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".contentpanel")),
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))
        ));
    }

    /**
     * Returns the number of cart items.
     */
    public int getCartCount() {
        openCart();
        List<WebElement> items = driver.findElements(cartItems);
        int count = items.size();
        ReportUtil.log(" Cart contains " + count + " item(s)");
        return count;
    }

    /**
     * Verifies the cart items match expected name/price.
     */
    public void verifyCartContentsAndTotal(List<String> expectedNames, List<String> expectedPrices) {
        openCart();
        List<WebElement> items = driver.findElements(cartItems);
        Assert.assertEquals(items.size(), expectedNames.size(), "🛒 Cart item count mismatch");

        double total = 0;

        for (int i = 0; i < items.size(); i++) {
            WebElement row = items.get(i);
            String actualName = row.findElement(productName).getText().trim();
            String actualPriceText = row.findElement(productPrice).getText().trim();

            ReportUtil.log(" Cart Item [" + i + "]: " + actualName + " | Price: " + actualPriceText);

            Assert.assertEquals(actualName, expectedNames.get(i), " Product name mismatch at index " + i);
            Assert.assertEquals(actualPriceText, expectedPrices.get(i), " Product price mismatch at index " + i);

            total += Double.parseDouble(actualPriceText.replace("$", "").trim());
        }

        ReportUtil.log("Total Cart Value: $" + String.format("%.2f", total));
    }

    /**
     * Proceeds to checkout after ensuring button is clickable.
     */
    public void proceedToCheckout() {
        openCart();

        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(checkoutBtn));
            scrollIntoView(button);
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        } catch (StaleElementReferenceException e) {
            ReportUtil.log(" StaleElement: Retrying click with JavaScript...");
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(checkoutBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        } catch (Exception e) {
            ReportUtil.log(" Checkout click failed: " + e.getMessage());
            throw e;
        }

        // Confirm navigation by waiting for checkout form or page title
        wait.until(ExpectedConditions.or(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#AccountFrm")),
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))
        ));

        ReportUtil.log(" Proceeded to checkout page");
    }

    /**
     * Scroll helper
     */
    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", element);
    }
}
