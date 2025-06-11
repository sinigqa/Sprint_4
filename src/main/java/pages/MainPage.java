package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    public WebElement getQuestion(int index) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.id("accordion__heading-" + index)));
    }

    public String getAnswerText(int index) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("accordion__panel-" + index))).getText();
    }

    public void clickOnQuestion(int index) {
        WebElement question = getQuestion(index);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center' });", question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
    }
}