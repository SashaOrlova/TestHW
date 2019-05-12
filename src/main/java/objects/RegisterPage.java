package objects;

import elements.ButtonElement;
import elements.TextElement;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private TextElement name;
    private TextElement mail;
    private TextElement login;
    private TextElement password;
    private TextElement confirmPassword;
    private ButtonElement button;

    public RegisterPage(WebDriver driver) {
        driver.navigate().to("http://localhost:8080/registerUserForm");
        name = new TextElement(driver, "l.R.user_fullName");
        mail = new TextElement(driver, "l.R.user_email");
        login = new TextElement(driver, "l.R.user_login");
        password = new TextElement(driver, "l.R.password");
        confirmPassword = new TextElement(driver, "l.R.confirmPassword");
        button = new ButtonElement(driver, "id_l.R.register");
    }

    public void register(String name, String mail, String login, String password, String confirmPassword) {
        this.name.setText(name);
        this.mail.setText(mail);
        this.login.setText(login);
        this.password.setText(password);
        this.confirmPassword.setText(confirmPassword);
        button.click();
    }
}
