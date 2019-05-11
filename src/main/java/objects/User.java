package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class User {
    WebElement userLink;
    String name;
    String mail;
    List<WebElement> groups;
    String lastVisit;
    List<WebElement> actions;

    public User(WebElement parentElement) {
        List<WebElement> rows = parentElement.findElements(By.tagName("td"));
        userLink = rows.get(0).findElement(By.tagName("a"));
        name = rows.get(1).findElement(By.tagName("div")).getText();
        mail = rows.get(2).findElement(By.tagName("div")).getText();
        groups = rows.get(3).findElements(By.tagName("a"));
        lastVisit = rows.get(4).findElement(By.tagName("div")).getText();
        actions = rows.get(5).findElements(By.tagName("a"));
    }
}
