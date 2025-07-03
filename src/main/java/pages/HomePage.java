package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //  Get all main categories listed in the navigation bar
    public List<String> getMainCategories() {
        List<String> categories = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.nav > li > a"));

        for (WebElement el : elements) {
            categories.add(el.getText().trim());
        }

        return categories;
    }

    //  Select category by visible name (like "Skincare", "Makeup")
    public void selectCategory(String categoryName) {
        List<WebElement> categories = driver.findElements(By.cssSelector("ul.nav > li > a"));
        for (WebElement category : categories) {
            if (category.getText().trim().equalsIgnoreCase(categoryName)) {
                category.click();
                break;
            }
        }
    }

    //  Go to homepage by clicking the store logo
    public void goHome() {
        driver.findElement(By.cssSelector("#logo a")).click();
    }
        


    //  Click the same category again (if page refreshed after adding to cart)
    public void selectSameCategoryAgain() {
        WebElement activeCategory = driver.findElement(By.cssSelector("ul.nav > li.active > a"));
        if (activeCategory != null) {
            activeCategory.click();
        }
    }
}
