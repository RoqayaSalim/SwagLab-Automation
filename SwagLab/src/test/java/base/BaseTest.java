package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
public class BaseTest {
protected WebDriver driver;
@Parameters("browser")
@BeforeMethod
    public void setup(@Optional("chrome") String browser){
    //String browser = System.getProperty("browser", "chrome");

    switch (browser.toLowerCase()) {
        case "chrome":
            driver = new ChromeDriver();
            break;

        case "firefox":
            driver = new FirefoxDriver();
            break;

        case "edge":
            driver = new EdgeDriver();
            break;

        default:
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
    }
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.navigate().to("https://www.saucedemo.com/");

}
@AfterMethod
    public void teardown(){
    if (driver != null){
        driver.quit();

    }


}

















}
