package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ReportUtil;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CategoryPage extends BasePage {

    private final By productNames = By.cssSelector(".prdocutname");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects a random product from the current category page.
     */
    public void selectRandomProduct() {
        List<WebElement> products = driver.findElements(productNames);
        if (products.isEmpty()) {
            throw new RuntimeException(" No products found in the selected category.");
        }

        WebElement randomProduct = products.get(new Random().nextInt(products.size()));
        ReportUtil.log("🛒 Adding product to cart: " + randomProduct.getText());
        randomProduct.click();
    }

    /**
     * Returns a list of all product names on the current category page.
     */
    public List<String> getAllProductNames() {
        return driver.findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Selects a product by its index on the category page.
     */
    public void selectProductByIndex(int index) {
        List<WebElement> products = driver.findElements(productNames);
        if (index >= 0 && index < products.size()) {
            WebElement selected = products.get(index);
            ReportUtil.log(" Selected product by index " + index + ": " + selected.getText());
            selected.click();
        } else {
            throw new IllegalArgumentException(" Invalid product index: " + index);
        }
    }
}
