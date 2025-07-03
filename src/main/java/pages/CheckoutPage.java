package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private  By createAccountLink = By.xpath("//a[contains(text(), 'Continue') and contains(@href,'account/create')]");
    

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void startRegistration() {
        driver.findElement(createAccountLink).click();
    }
}
