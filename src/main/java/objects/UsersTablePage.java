package objects;

import elements.ButtonElement;
import elements.TextElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class UsersTablePage {
    private TextElement search;
    private TextElement group;
    private TextElement role;
    private TextElement project;
    private TextElement rights;
    private WebElement onlineOnly;
    private ButtonElement find;
    private ButtonElement clear;

    private List<User> users = new ArrayList<>();

    public UsersTablePage(WebDriver driver) {
        driver.navigate().to("http://localhost:8080/users");
        driver.findElement(By.name("l.U.queryText"));
        search = new TextElement(driver, "l.U.queryText");
        group = new TextElement(driver, "l.U.groupFilter");
        role = new TextElement(driver, "l.U.roleFilter");
        project = new TextElement(driver, "l.U.projectFilter");
        rights = new TextElement(driver, "l.U.permissionFilter");

        find = new ButtonElement(driver, "id_l.U.searchButton");
        clear = new ButtonElement(driver, "id_l.U.resetButton");

        onlineOnly = driver.findElement(By.id("id_l.U.onlineOnlyLabel"));

        List<WebElement> totalLinks = driver.findElement(By.id("id_l.U.usersList.usersList")).findElements(By.tagName("tr"));
        for (WebElement element: totalLinks) {
            users.add(new User(element));
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setSearch(String text) {
        this.search.setText(text);
    }

    public void setGroup(String text) {
        this.group.setText(text);
    }

    public void setRole(String text) {
        this.role.setText(text);
    }

    public void setProject(String text) {
        this.project.setText(text);
    }

    public void setRights(String text) {
        this.rights.setText(text);
    }

    public void clickFind() {
        this.find.click();
    }

    public void clickClear() {
        this.clear.click();
    }

    public void changeOnline() {
        onlineOnly.click();
    }
}
