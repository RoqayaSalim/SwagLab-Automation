package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    // Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector(
            "[data-test='error']"
    );

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

// enter user name
    public void enterUsername(String username){

driver.findElement(usernameField).sendKeys(username);

    }

//enter passwor
    public void enterPassword(String password){
      driver.findElement(passwordField).sendKeys(password);

    }
// click login
public void clickLogin(){
 driver.findElement(loginButton) .click();

}
    // Complete login action
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

// Read Error Message
public String getErrorMessage() {
return driver.findElement(errorMessage).getText();

}




















}
