package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;
//import org.openqa.selenium.By;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
public class CheckoutTest extends BaseTest {

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private List<String> products;
    private JsonNode data;
    ;
    private JsonNode Info;

    @BeforeMethod
    public void setUp() {
        data = DataDriven.jsonReader();
        // private JsonNode info;

// in setUp(), after data = DataDriven.jsonReader();
        Info = data.get("checkoutInfo");
        String username = data.get("validLogin").get("username").asText();
        String password = data.get("validLogin").get("password").asText();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        products = new ArrayList<>();
        for (JsonNode p : data.get("cartProducts")) {
            products.add(p.asText());
        }

        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void verifyCartTotalPrice() {
        //JsonNode Info = data.get("checkoutInfo");

        // 1. Read prices from the Inventory page BEFORE adding, then sum them
        BigDecimal expectedTotal = BigDecimal.ZERO;
        for (String product : products) {
            expectedTotal = expectedTotal.add(inventoryPage.getProductPrice(product));
        }

        // 2. Add the 3 products
        for (String product : products) {
            cartPage.addProductToCart(product);
        }

        // 3. Cart -> Checkout -> fill info -> Continue
        inventoryPage.openCart();
        cartPage.clickCheckout();

        checkoutPage.fillInformation(
                Info.get("firstName").asText(),
                Info.get("lastName").asText(),
                Info.get("postalCode").asText());
        checkoutPage.clickContinue();

        // 4. Compare
        BigDecimal actualTotal = checkoutPage.getItemTotal();
        Assert.assertEquals(actualTotal.compareTo(expectedTotal), 0,
                "Expected total " + expectedTotal + " but checkout shows " + actualTotal);
    }

    //@Test

    @Test
    public void verifyCheckoutWithEmptyCartShowsErrorOrBlocked() {
        // 1. Make sure the cart is empty
        inventoryPage.openCart();
        if (!cartPage.isCartEmpty()) {
            cartPage.removeAllItems();
        }
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty before the test");

        // 2. Checkout -> fill data -> Continue -> Finish
        cartPage.clickCheckout();
        checkoutPage.fillInformation(
                Info.get("firstName").asText(),
                Info.get("lastName").asText(),
                Info.get("postalCode").asText());
        checkoutPage.clickContinue();
        checkoutPage.ClickFinish();

        // 3. Read the message that appeared
        String actualMessage = checkoutPage.getDisplayedMessage().toLowerCase();

        // 4. Must contain "error" or "blocked", otherwise it's a BUG
        Assert.assertTrue(
                actualMessage.contains("error") || actualMessage.contains("blocked"),
                "BUG: checkout with an empty cart should show an error or be blocked, but the message was: \"" + actualMessage + "\"");
    }







}




