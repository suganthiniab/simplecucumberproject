package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

    @Given("User opens chrome browser")
    public void user_opens_chrome_browser() {
        driver = new ChromeDriver();
    }

    @Given("User is on Log in page")
    public void user_is_on_log_in_page() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("User enters valid username standard_user and password secret_sauce")
    public void user_enters_valid_username_standard_user_and_password_secret_sauce() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
    }

    @Then("User should successfully login")
    public void user_should_successfully_login() {
        driver.findElement(By.id("login-button")).click();
    }

    @Then("User should be on inventory page")
    public void user_should_be_on_inventory_page() {
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(expectedUrl.equals(actualUrl));
    }

    @When("User enters valid username standard_user and invalid password secret_sau")
    public void user_enters_valid_username_standard_user_and_invalid_password_secret_sau() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sau");
        driver.findElement(By.id("login-button")).click();
    }

    @Then("Login should fail")
    public void login_should_fail() {

        String expectedFlashText = "Epic sadface: Username and password do not match any user in this service";
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        WebElement errorFlash = driver.findElement(By.xpath("//h3/button[@class='error-button']"));
        wait.until(ExpectedConditions.visibilityOf(errorFlash));
        String actualFlashText = errorFlash.getText().trim();
        Assert.assertTrue(expectedFlashText.contains(actualFlashText));

    }

    @Then("User should be on login page")
    public void user_should_be_on_login_page() {
        String expectedPageUrl = "https://www.saucedemo.com/";
        String actualPageUrl = driver.getCurrentUrl();
        Assert.assertTrue(expectedPageUrl.contains(actualPageUrl));
    }

    @And("User exits the browser")
    public void userExitsTheBrowser() {
        driver.close();
    }
}
