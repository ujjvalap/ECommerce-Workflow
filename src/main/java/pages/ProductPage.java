package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ReportUtil;

import java.time.Duration;
import java.util.*;

public class ProductPage extends BasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By productBlocks = By.cssSelector("a.prdocutname"); // product list items
    private final By productName = By.cssSelector("h1.productname");
    private final By productPrice = By.cssSelector(".productinfo .price"); 
    private final By addToCartBtn = By.cssSelector(".cart"); // cart button on detail page

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Selects a random product from the current category page.
     */
    public void selectRandomProduct() {
        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productBlocks));

        if (products.isEmpty()) {
            throw new RuntimeException(" No products found on the category page.");
        }

        WebElement randomProduct = products.get(new Random().nextInt(products.size()));
        String name = randomProduct.getText().trim();
        ReportUtil.log("🔍 Selecting product: " + name);

        randomProduct.click();
    }

    /**
     * Gets the name of the current product.
     */
    public String getProductName() {
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        return nameEl.getText().trim();
    }

    /**
     * Gets the price of the current product.
     */
    // public String getProductPrice() {
    //     WebElement priceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
    //     return priceEl.getText().trim();
    // }

    public String getProductPrice() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
    By.cssSelector("div.price > span"))); // this is the failing selector
    return priceElement.getText();
}


    /**
     * Adds the current product to cart.
     */
    public void addToCart() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(addToCartBtn));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", button);

        wait.until(ExpectedConditions.elementToBeClickable(button));

        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            ReportUtil.log(" Normal click failed, using JS click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }

        // Wait for confirmation alert/cart update
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-info"))
        ));

        ReportUtil.log(" Product added to cart successfully");
    }

    /**
     * Returns all product names from the listing page.
     */
    public List<String> getAllProductNames() {
        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productBlocks));
        List<String> names = new ArrayList<>();

        for (WebElement e : products) {
            names.add(e.getText().trim());
        }

        return names;
    }

    /**
     * Selects a product by index on the category page.
     */
    public void selectProductByIndex(int index) {
        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productBlocks));

        if (products.isEmpty() || index >= products.size()) {
            throw new RuntimeException(" No product found at index " + index);
        }

        WebElement selected = products.get(index);
        ReportUtil.log(" Selecting product at index " + index + ": " + selected.getText());
        selected.click();
    }
}
