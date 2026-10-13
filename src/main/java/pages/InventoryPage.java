package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class InventoryPage {
    private WebDriver driver;

    // Locators
    private By pageTitle = By.className("app_logo");
    private By cartIcon = By.className("shopping_cart_link");
    private By inventoryItems = By.className("inventory_item");

    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
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














}
