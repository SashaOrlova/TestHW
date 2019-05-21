import elements.ButtonElement;
import elements.TextElement;
import objects.RegisterPage;
import objects.User;
import objects.UsersTablePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

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

    private void check(String login_str, String name_str, String pass_str, String email_str, WebDriver driver) {
        driver.navigate().to("http://localhost:8080/login");
        TextElement login = new TextElement(driver, "l.L.login");
        TextElement password = new TextElement(driver, "l.L.password");
        ButtonElement loginButton = new ButtonElement(driver, "id_l.L.loginButton");
        login.setText(login_str);
        password.setText(pass_str);
        loginButton.click();
        driver.navigate().to("http://localhost:8080/user");
        login = new TextElement(driver, "l.U.tabs.gst.loginDisplay");
        TextElement name = new TextElement(driver, "l.U.tabs.gst.fullNameText");
        TextElement email = new TextElement(driver, "l.U.tabs.gst.emailText");
        Assert.assertEquals(name_str, name.getText());
        Assert.assertEquals(login_str, login.getText());
        Assert.assertEquals(email_str, email.getText());
    }

    @Test
    public void testRegisterUser(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("test test", "test@mail.ru", "test1", "pass", "pass");
        check("test1", "test test","pass", "test@mail.ru", driver);
    }

    @Test
    public void testRegisterUserFailTwice(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("test test", "test@mail.ru", "test2", "pass", "pass");
        registerPage = new RegisterPage(driver);
        WebElement element = driver.findElement(By.name("l.R.user_login"));
        Function<? super WebDriver, Object> isTextPresent = (ExpectedCondition<Object>) webDriver -> element.getAttribute("class").equals("jt-input login-input form-has-error");
        registerPage.register("test test", "test@mail.ru", "test2", "pass", "pass");
        Wait<WebDriver> wait = new WebDriverWait(driver, 30, 1000);
        wait.until(isTextPresent);
        Assert.assertTrue(true);
    }

    @Test
    public void testRegisterUserFailEmpty(){
        RegisterPage registerPage = new RegisterPage(driver);
        WebElement element = driver.findElement(By.name("l.R.user_login"));
        Function<? super WebDriver, Object> isTextPresent = (ExpectedCondition<Object>) webDriver -> element.getAttribute("class").equals("jt-input login-input form-has-error");
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        registerPage.register("test test", "test@mail.ru", "", "pass", "pass");
        wait.until(isTextPresent);
        Assert.assertTrue(true);
    }

    @Test
    public void testRegisterUserLanguage(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("тест тест", "test@mail.ru", "тест", "пароль", "пароль");
        check("тест", "тест тест", "пароль", "test@mail.ru", driver);
    }

    @Test
    public void testRegisterUserFailPassword(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("test test", "test@mail.ru", "", "pass1", "pass2");
        WebElement element = driver.findElement(By.name("l.R.confirmPassword"));
        Function<? super WebDriver, Object> isTextPresent = (ExpectedCondition<Object>) webDriver -> element.getAttribute("class").equals("jt-input login-input form-has-error");
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(isTextPresent);
        Assert.assertTrue(true);
    }

    @Test
    public void testRegisterUserDifferentLanguagesLogin(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("test", "test@mail.ru", "testТЕСТ", "пароль", "пароль");
        check("testТЕСТ", "test", "пароль", "test@mail.ru", driver);
    }

    @Test
    public void testRegisterUserSpaceLogin(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("test", "test@mail.ru", "test ТЕСТ", "пароль", "пароль");
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className("errorSeverity")));
        Assert.assertEquals("Restricted character ' ' in the name", element.getText());
    }

    @Test
    public void testRegisterUserStrangeSymbolsLogin(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("test", "test@mail.ru", "$%@#$!@$*&-_)()()\\n", "пароль", "пароль");
        check("$%@#$!@$*&-_)()()\\n", "test", "пароль", "test@mail.ru", driver);
    }

    @Test
    public void testRegisterUserStrangeSymbolsName(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("$%@#$!@$*&-_)()()\\n", "test@mail.ru", "test12", "пароль", "пароль");
        check("test12","$%@#$!@$*&-_)()()\\n",  "пароль", "test@mail.ru", driver);
    }

    @Test
    public void testRegisterUserSpacesName(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("t e    s t", "test@mail.ru", "test34", "пароль", "пароль");
        check("test34", "t e    s t", "пароль", "test@mail.ru", driver);
    }

    @Test
    public void testRegisterUserNumbersLogin(){
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register("t e    s t", "test@mail.ru", "12345678", "пароль", "пароль");
        check("12345678", "t e    s t", "пароль", "test@mail.ru", driver);
    }
}
