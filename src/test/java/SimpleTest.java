import objects.RegisterPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SimpleTest {
    private WebDriver driver;

    @Before
    public void setup(){
        String pathToChromeDriver = "chromedriver";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        driver = new ChromeDriver();

    }

    @After
    public void teardown(){
        driver.close();
    }

    @Test
    public void testRegisterUser(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("testuser0");
        Wait<WebDriver> wait = new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("http://localhost:8080/registerUserForm")));
        Assert.assertEquals("http://localhost:8080/dashboard", driver.getCurrentUrl());
    }

    @Test
    public void testRegisterUserFailTwice(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("testuser9");
        Wait<WebDriver> wait = new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("http://localhost:8080/registerUserForm")));
        registerPage = new RegisterPage(driver);
        registerPage.register("testuser9");
        WebElement element = driver.findElement(By.name("l.R.user_fullName"));
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
        Assert.assertNotEquals("http://localhost:8080/dashboard", driver.getCurrentUrl());
    }

    @Test
    public void testRegisterUserFailLanguage(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("");
        Wait<WebDriver> wait = new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class);
        WebElement element = driver.findElement(By.name("l.R.user_fullName"));
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
        Assert.assertEquals("http://localhost:8080/registerUserForm", driver.getCurrentUrl());
    }
}
