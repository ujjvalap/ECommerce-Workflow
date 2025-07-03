package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class RegistrationPage extends BasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstName = By.id("AccountFrm_firstname");
    private final By lastName = By.id("AccountFrm_lastname");
    private final By email = By.id("AccountFrm_email");
    private final By telephone = By.id("AccountFrm_telephone");
    private final By address = By.id("AccountFrm_address_1");
    private final By city = By.id("AccountFrm_city");
    private final By postcode = By.id("AccountFrm_postcode");
    private final By country = By.id("AccountFrm_country_id");
    private final By region = By.id("AccountFrm_zone_id");
    private final By password = By.id("AccountFrm_password");
    private final By confirmPassword = By.id("AccountFrm_confirm");
    private final By agreeCheckbox = By.id("AccountFrm_agree");
    private final By submitBtn = By.cssSelector("button[title='Continue']");
    private final By errorMessage = By.cssSelector(".alert");

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Fills the registration form with provided data.
     *
     * @param data          A map of field names to values
     * @param skipLastName  If true, skip the last name field (for negative test)
     */
    public void fillForm(Map<String, String> data, boolean skipLastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));

        if (data.get("firstname") != null)
            driver.findElement(firstName).sendKeys(data.get("firstname"));

        if (!skipLastName && data.get("lastname") != null)
            driver.findElement(lastName).sendKeys(data.get("lastname"));

        if (data.get("email") != null)
            driver.findElement(email).sendKeys(data.get("email"));

        if (data.get("telephone") != null)
            driver.findElement(telephone).sendKeys(data.get("telephone"));

        if (data.get("address") != null)
            driver.findElement(address).sendKeys(data.get("address"));

        if (data.get("city") != null)
            driver.findElement(city).sendKeys(data.get("city"));

        if (data.get("postcode") != null)
            driver.findElement(postcode).sendKeys(data.get("postcode"));

        try {
            // Select Country
            if (data.get("country") != null) {
                Select countrySelect = new Select(driver.findElement(country));
                countrySelect.selectByVisibleText(data.get("country"));
            }

            // Wait for Region to be populated (max 5s)
            wait.until(ExpectedConditions.presenceOfElementLocated(region));
            Thread.sleep(1000); // Sometimes region loads with JS delay

            // Re-locate Region element after potential DOM update
            if (data.get("region") != null) {
                Select regionSelect = new Select(driver.findElement(region));
                boolean found = false;

                for (WebElement option : regionSelect.getOptions()) {
                    if (option.getText().trim().equalsIgnoreCase(data.get("region").trim())) {
                        regionSelect.selectByVisibleText(option.getText().trim());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("⚠️ Region '" + data.get("region") + "' not found. Available options:");
                    for (WebElement option : regionSelect.getOptions()) {
                        System.out.println(" - " + option.getText().trim());
                    }
                    // Optional fallback
                    // regionSelect.selectByIndex(1);
                }
            }
        } catch (Exception e) {
            System.out.println(" Country or Region selection failed: " + e.getMessage());
        }

        if (data.get("password") != null)
            driver.findElement(password).sendKeys(data.get("password"));

        if (data.get("confirm") != null)
            driver.findElement(confirmPassword).sendKeys(data.get("confirm"));

        WebElement checkbox = driver.findElement(agreeCheckbox);
        if (!checkbox.isSelected()) checkbox.click();
    }

    /**
     * Submits the registration form.
     */
    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
    }

    /**
     * Returns true if error alert is displayed.
     */
    public boolean isErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }
}
