package pages;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.JsonNode;
import utils.DataDriven;
//import pages.LoginPage;
import pages.InventoryPage;
//import pages.CartPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class CartPage {
    private  WebDriver driver;
   // private By cartIcon = By.className("shopping_cart_link");
    private By cartItems = By.className("cart_item");
    private By cartItemNames = By.className("inventory_item_name");
    private By removeButtons = By.cssSelector("button[id^='remove-']");

    public CartPage(WebDriver driver){
        this.driver=driver;


    }

    public void clickCheckout() {
        driver.findElement(By.id("checkout")).click();
    }
    private String toSlug(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }
    // Button on the inventory page
    public void addProductToCart(String productName) {
        driver.findElement(By.id("add-to-cart-" + toSlug(productName))).click();
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    // Works on both inventory and cart pages
    public void removeProduct(String productName) {
            By locator = By.id("remove-" + toSlug(productName));
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(locator))
                    .click();
        }


    public List<String> getCartProductNames() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItemNames));
        List<String> names = new ArrayList<>();
        for (WebElement item : driver.findElements(cartItemNames)) {
            names.add(item.getText());
        }
        return names;

}
public int getCartItemsCount(){

   return driver.findElements(cartItems).size();

}



    public boolean isCartEmpty() {
        return driver.findElements(cartItems).isEmpty();
    }

    public void removeAllItems() {
        // private By removeButtons = By.cssSelector("button[id^='remove-']");


        List<WebElement> buttons = driver.findElements(removeButtons);
        while (!buttons.isEmpty()) {
            buttons.get(0).click();
            buttons = driver.findElements(removeButtons);

        }


    }}



