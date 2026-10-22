package pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CheckoutPage {
    private WebDriver driver;

    // Locators
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By itemTotalLabel = By.className("summary_subtotal_label");
    private By FinishButton = By.id("finish");
    private By Message = By.cssSelector("[data-test='error'], .complete-header");
  ;
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }
    public void ClickFinish() {
        driver.findElement(FinishButton).click();
    }
    public String getDisplayedMessage() {
        List<WebElement> elements = driver.findElements(Message);
        return elements.isEmpty() ? "" : elements.get(0).getText();
    }


    // "Item total: $59.97" -> 59.97
    public BigDecimal getItemTotal() {
        String text = driver.findElement(itemTotalLabel).getText();
        return new BigDecimal(text.replaceAll("[^0-9.]", ""));
    }



        }