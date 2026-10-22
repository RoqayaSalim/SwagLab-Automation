package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

import java.time.Duration;

public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;
    private String originalWindow;

    @BeforeMethod
    public void loginFirst() {
        JsonNode data = DataDriven.jsonReader();

        String username = data.get("validLogin").get("username").asText();
        String password = data.get("validLogin").get("password").asText();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        inventoryPage = new InventoryPage(driver);
        originalWindow = inventoryPage.getWindowHandle();
    }

    private void waitForUrlToContain(String text) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(text));
    }

    @Test
    public void verifyInventoryPageElements() {
        Assert.assertEquals(
                inventoryPage.getPageTitle(),
                "Swag Labs",
                "Page title is incorrect"
        );

        Assert.assertTrue(
                inventoryPage.isCartDisplayed(),
                "Cart icon is not displayed"
        );

        Assert.assertEquals(
                inventoryPage.getProductsCount(),
                6,
                "Inventory page should display 6 products"
        );
    }

    @Test
    public void verifyLinkedinLinkOpensLinkedin() {
        inventoryPage.clicklinkedin();
        inventoryPage.switchToNewTab();
        waitForUrlToContain("linkedin");

        Assert.assertTrue(inventoryPage.getCurrentUrl().contains("linkedin"),
                "URL was: " + inventoryPage.getCurrentUrl());

        inventoryPage.closeNewTabAndSwitchBack(originalWindow);
    }

    @Test
    public void verifyFacebookLinkOpensFacebook() {
        inventoryPage.clickfacebook();
        inventoryPage.switchToNewTab();
        waitForUrlToContain("facebook");

        Assert.assertTrue(inventoryPage.getCurrentUrl().contains("facebook"),
                "URL was: " + inventoryPage.getCurrentUrl());

        inventoryPage.closeNewTabAndSwitchBack(originalWindow);
    }

    @Test
    public void verifyTwitterLinkOpensX() {
        inventoryPage.clicktwitter();
        inventoryPage.switchToNewTab();
        waitForUrlToContain("x.com");

        Assert.assertTrue(inventoryPage.getCurrentUrl().contains("x.com"),
                "URL was: " + inventoryPage.getCurrentUrl());

        inventoryPage.closeNewTabAndSwitchBack(originalWindow);
    }


}