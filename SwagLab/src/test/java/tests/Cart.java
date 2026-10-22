package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

import java.util.ArrayList;
import java.util.List;

public class Cart extends BaseTest {

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private List<String> products;

    @BeforeMethod
    public void setUp() {
        JsonNode data = DataDriven.jsonReader();

        String username = data.get("validLogin").get("username").asText();
        String password = data.get("validLogin").get("password").asText();

        // Login successfully
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // Read product names from JSON (not hardcoded)
        products = new ArrayList<>();
        for (JsonNode p : data.get("cartProducts")) {
            products.add(p.asText());
        }

        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    public void verifyCartIsEmpty() {
        inventoryPage.openCart();

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart should be empty after login"
        );
    }

    @Test
    public void verifyThreeProductsAddedInSameOrder() {
        for (String product : products) {
            cartPage.addProductToCart(product);
        }

        inventoryPage.openCart();

        Assert.assertEquals(
                cartPage.getCartProductNames(),
                products,
                "Cart items or their order do not match the added products"
        );
    }

    @Test
    public void verifyRemoveOneProduct() {
        for (String product : products) {
            cartPage.addProductToCart(product);
        }

        inventoryPage.openCart();

        // Remove the second product
        String removed = products.get(1);
        cartPage.removeProduct(removed);

        List<String> expected = new ArrayList<>(products);
        expected.remove(removed);

        Assert.assertEquals(cartPage.getCartItemsCount(), 2,
                "Cart should contain 2 items after removal");
        Assert.assertEquals(cartPage.getCartProductNames(), expected,
                "Remaining items are not correct");
        Assert.assertFalse(cartPage.getCartProductNames().contains(removed),
                removed + " should not be in the cart");
    }
    @Test
    public void verifyCartStateAfterLogoutAndLogin() {
        // 1. Add 2 products (already logged in from @BeforeMethod)
        List<String> added = new ArrayList<>(products.subList(0, 2));
        for (String product : added) {
            cartPage.addProductToCart(product);
        }

        // 2. Logout
        inventoryPage.logout();

        // 3. Login again with the same user
        JsonNode data = DataDriven.jsonReader();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                data.get("validLogin").get("username").asText(),
                data.get("validLogin").get("password").asText());

        // 4. Open the cart and check
        inventoryPage.openCart();

        // Observed behavior: items are still in the cart after re-login.
        // If your manual check shows the cart is empty, replace with:
        // Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after re-login");
        Assert.assertEquals(cartPage.getCartProductNames(), added,
                "Cart items after logout/login differ from what was added");
    }
}