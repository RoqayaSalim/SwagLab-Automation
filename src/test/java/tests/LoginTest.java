package tests;
import base.BaseTest;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.DataDriven;

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.Test;
public class LoginTest extends BaseTest {


    // Test 1 Succsseful login
    @Test
    public void VerifySuccssefulLogin(){
     JsonNode data = DataDriven.jsonReader();
     String username = data.get("validLogin").get("username").asText();
String password = data.get("validLogin").get("password").asText();
LoginPage loginPage = new LoginPage(driver);
loginPage.login(username,password);
Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),"User was not redirected to the Inventory page");

    }
// Test 2 invalid login

    @Test
    public void VerifyInvalidLogin() {
        JsonNode data = DataDriven.jsonReader();
        String username = data.get("invalidLogin").get("username").asText();
        String password = data.get("invalidLogin").get("password").asText();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        String actualMessage = loginPage.getErrorMessage();

        Assert.assertTrue(actualMessage.contains(
                        "Username and password do not match"
                ),
                "Unexpected error message: " + actualMessage
        );


    }
    // Test 3: Login without password
    @Test
    public void verifyLoginWithoutPassword() {

        JsonNode data = DataDriven.jsonReader();

        String username = data.get("loginWithoutPassword")
                .get("username").asText();

        String password = data.get("loginWithoutPassword")
                .get("password").asText();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        String actualMessage = loginPage.getErrorMessage();

        Assert .assertTrue(
                actualMessage.contains("Password is required"),
                "Unexpected error message: " + actualMessage

        );
    }












}
