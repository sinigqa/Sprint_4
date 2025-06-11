package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    //  Локаторы формы
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    //  Кнопки "Заказать"
    private final By topOrderButton = By.cssSelector("button.Button_Button__ra12g");
    private final By bottomOrderButton = By.xpath("(//button[text()='Заказать'])[last()]");

    // Баннер с куки
    private final By cookieBanner = By.id("rcc-confirm-button");

    //  Дата доставки
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    //  Выбор срока аренды
    private final By rentalPeriodField = By.xpath("//div[@class='Dropdown-placeholder' and text()='* Срок аренды']");
    private final By periodOneDay = By.xpath("//div[text()='сутки']");
    private final By periodTwoDays = By.xpath("//div[text()='двое суток']");

    //  Цвет самоката
    private final By blackColorOption = By.id("black");
    private final By greyColorOption = By.id("grey");

    //  Далее
    private final By nextButton = By.cssSelector("button.Button_Middle__1CSJM");


    // Подтверждение заказа
    private final By confirmYesButton = By.xpath("//button[text()='Да']");
    private final By successMessage = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and text()='Заказ оформлен']");


    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    public void closeCookieBannerIfPresent() {
        WebElement banner = driver.findElement(cookieBanner);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);
    }

    public void setName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
    }

    public void setSurname(String surname) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(surnameField)).sendKeys(surname);
    }

    public void setAddress(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressField)).sendKeys(address);
    }

    public void setMetro(String station) {
        WebElement metroInput = wait.until(ExpectedConditions.visibilityOfElementLocated(metroField));
        metroInput.click();

        By stationLocator = By.xpath("//div[text()='" + station + "']");
        wait.until(ExpectedConditions.elementToBeClickable(stationLocator)).click();
    }

    public void setPhone(String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneField)).sendKeys(phone);
    }

    public void clickTopOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(topOrderButton)).click();
    }

    public void clickBottomOrderButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(bottomOrderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void setDate(String date) {
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER); // Закрытие календаря
    }

    public void selectRentalPeriod(String period) {
        WebElement periodField = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        periodField.click();

        By periodLocator = period.equals("сутки") ? periodOneDay : periodTwoDays;

        wait.until(ExpectedConditions.elementToBeClickable(periodLocator)).click();
    }

    public void selectBlackColor() {
        wait.until(ExpectedConditions.elementToBeClickable(blackColorOption)).click();
    }

    public void selectGreyColor() {
        wait.until(ExpectedConditions.elementToBeClickable(greyColorOption)).click();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
