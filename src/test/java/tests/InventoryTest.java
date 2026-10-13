package tests;
import base.BaseTest;
import pages.LoginPage;
import pages.InventoryPage;
import utils.DataDriven;

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.Test;
public class InventoryTest extends BaseTest{
    @Test
    public void verifyInventoryPageElements() {

        // Read valid credentials from JSON
        JsonNode data = DataDriven.jsonReader();

        String username = data.get("validLogin")
                .get("username").asText();

        String password = data.get("validLogin")
                .get("password").asText();

        // Login successfully
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // Create InventoryPage object
        InventoryPage inventoryPage = new InventoryPage(driver);
// Verify page title
        Assert.assertEquals(
                inventoryPage.getPageTitle(),
                "Swag Labs",
                "Page title is incorrect"
        );

        // Verify cart icon
        Assert.assertTrue(
                inventoryPage.isCartDisplayed(),
                "Cart icon is not displayed"
        );

        // Verify number of products
        Assert.assertEquals(
                inventoryPage.getProductsCount(),
                6,
                "Inventory page should display 6 products"
        );
    }










}
