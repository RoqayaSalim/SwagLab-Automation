package pages;
import java.math.BigDecimal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class InventoryPage {
    private WebDriver driver;

    // Locators
    private By pageTitle = By.className("app_logo");
    private By cartIcon = By.className("shopping_cart_link");
    private By inventoryItems = By.className("inventory_item");
   // private By productNames = By.className("inventory_item_name");
   // private By productPrices = By.className("inventory_item_price");
   private By twitterlink = By.cssSelector("[data-test='social-twitter']");
    private By facebooklink = By.cssSelector("a[href*='facebook.com/saucelabs']");
    private By linkedinlink = By.className("social_linkedin");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");

    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    // Get page title text
    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    // Check if cart icon is displayed
    public boolean isCartDisplayed() {
        return driver.findElement(cartIcon).isDisplayed();
    }

    // Get number of products
    public int getProductsCount() {
        return driver.findElements(inventoryItems).size();
    }


    public void clickfacebook() {
        driver.findElement(facebooklink).click();
    }

    public void clicktwitter() {
        driver.findElement(twitterlink).click();
    }


    public void clicklinkedin() {
        driver.findElement(linkedinlink).click();
    }

    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }

    public void switchToNewTab() {
        String original = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                return;
            }
        }
    }

    public void closeNewTabAndSwitchBack(String originalHandle) {
        driver.close();
        driver.switchTo().window(originalHandle);
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public BigDecimal getProductPrice(String productName) {
        By priceLocator = By.xpath(
                "//div[@class='inventory_item_name ' and normalize-space(.)='" + productName + "']"
                        + "/ancestor::div[@class='inventory_item']"
                        + "//div[@class='inventory_item_price']");
        String text = driver.findElement(priceLocator).getText();
        return new BigDecimal(text.replaceAll("[^0-9.]", ""));
    }

    public void logout() {
        driver.findElement(menuButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(logoutLink))
                .click();
    }
}