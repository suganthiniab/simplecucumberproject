package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;

public class CartAmountSteps {

    WebDriver driver;

    double expectedTotalCartPrice ;
    double expectedRoundedTaxAmountNew ;
    double expectedTotal ;

    public CartAmountSteps()
    {
        this.driver = BaseSteps.driver;
    }

    @And("User adds Bolt T-Shirt and Red T-Shirt to the cart")
    public void userAddsBoltTShirtAndRedTShirtToTheCart() {
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-bolt-t-shirt"))).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
    }

    @And("User clicks on Cart icon")
    public void userClicksOnCartIcon() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-test='shopping-cart-link']"))).click();
        driver.findElement(By.xpath("//a[@data-test='shopping-cart-link']")).click();
    }

    @And("User clicks on Checkout button")
    public void userClicksOnCheckoutButton() {
        driver.findElement(By.id("checkout")).click();
    }

    @And("User enters First Name as Aravindth, Last Name as Sathees , Zip code as {int}")
    public void userEntersFirstNameAsAravindthLastNameAsSatheesZipCodeAs(int arg0) {
        driver.findElement(By.id("first-name")).sendKeys("Aravindth");
        driver.findElement(By.id("last-name")).sendKeys("Sathees");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
    }

    @And("User clicks on Continue button")
    public void userClicksOnContinueButton() {
        driver.findElement(By.id("continue")).click();
    }

    @And("User clicks on Finish button")
    public void userClicksOnFinishButton() {
        driver.findElement(By.id("finish")).click();
    }

    @Then("Thank you for your order! should be displayed to the user")
    public void thankYouForYourOrderShouldBeDisplayedToTheUser() {
        String expectedLabel = "Thank you for your order!";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String actualLabel = driver.findElement(By.xpath("//div/h2")).getText().trim();
        Assert.assertTrue(expectedLabel.contains(actualLabel));
    }

    @And("User should be on checkout-complete page")
    public void userShouldBeOnCheckoutCompletePage() {
        String expectedCheckoutUrl = "https://www.saucedemo.com/checkout-complete.html";
        String actualCheckoutUrl = driver.getCurrentUrl().trim();
        Assert.assertTrue(expectedCheckoutUrl.contains(actualCheckoutUrl));
    }

    @And("User reads and stores the prices of the products")
    public void userReadsAndStoresThePricesOfTheProducts() {

        List<WebElement> prices = driver.findElements(By.xpath("//div[@data-test='inventory-item-price']"));
        for(WebElement price : prices){
            String priceOfProduct = price.getText().replace("$", "");
            double priceOfProductNew = Double.parseDouble(priceOfProduct);
            expectedTotalCartPrice += priceOfProductNew;
        }
        System.out.println("Expected Total Cart Price: " + expectedTotalCartPrice);

    }

    @Then("The total of the added products should be displayed correctly on Item total label")
    public void theTotalOfTheAddedProductsShouldBeDisplayedCorrectlyOnItemTotalLabel() {
        String actualItemTotal = driver.findElement(By.xpath("//div[@data-test='subtotal-label']"))
                .getText().replace("Item total: $", "");
        double actualItemTotalNew = Double.parseDouble(actualItemTotal);

        Assertions.assertEquals(expectedTotalCartPrice, actualItemTotalNew);
    }

    @And("{int} percent of tax should be displayed correctly on Tax label")
    public void percentOfTaxShouldBeDisplayedCorrectlyOnTaxLabel(int arg0) {
        double tax = 8.0;
        double expectedTaxAmount;

        expectedTaxAmount = expectedTotalCartPrice * 8/100;
        DecimalFormat formatter = new DecimalFormat("#.##");
        String expectedRoundedTaxAmount = formatter.format(expectedTaxAmount);
        expectedRoundedTaxAmountNew = Double.parseDouble(expectedRoundedTaxAmount);
        System.out.println("Expected Tax: " + expectedRoundedTaxAmountNew);

        String actualTaxAmount = driver.findElement(By.xpath("//div[@data-test='tax-label']")).getText()
                .replace("Tax: $", "");
        double actualTaxAmountNew = Double.parseDouble(actualTaxAmount);

        Assertions.assertEquals(expectedRoundedTaxAmountNew, actualTaxAmountNew, "Expected and Actual tax not equal");
    }

    @And("Validate Total Label")
    public void validateTotalLabel() {

        //expectedTotal = expectedTotalCartPrice + expectedRoundedTaxAmountNew;
        expectedTotal = expectedTotalCartPrice + expectedRoundedTaxAmountNew;
        System.out.println("Expected Total: " +expectedTotal);
        String actualTotal = driver.findElement(By.xpath("//div[@data-test='total-label']")).getText()
                .replace("Total: $", "");
        double actualTotalNew = Double.parseDouble(actualTotal);

       Assertions.assertEquals(expectedTotal, actualTotalNew);
    }
}
