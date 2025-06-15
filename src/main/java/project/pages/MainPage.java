package project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement findQuestionElementByText(String questionText) {
        List<WebElement> questions = driver.findElements(By.className("accordion__button"));

        for (WebElement question : questions) {
            if (question.getText().equals(questionText)) {
                return question;
            }
        }

       return null;
    }

    public void clickOnQuestionByText(String questionText) {
        WebElement question = findQuestionElementByText(questionText);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center' });", question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
    }

    public String getAnswerByText(String questionText) {
        WebElement question = findQuestionElementByText(questionText);
        assert question != null;
        String panelId = question.getAttribute("aria-controls");


        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId))).getText();
    }
}