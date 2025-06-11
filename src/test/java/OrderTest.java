import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.OrderPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {

    private WebDriver driver;
    private OrderPage orderPage;

    private final String testName;
    private final String browser;
    private final String buttonLocation; 

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String color;
    private final String rentalPeriod;
    private final String deliveryDate;

    public OrderTest(String testName, String browser, String buttonLocation,
                     String name, String surname, String address, String metro, String phone, String color, String rentalPeriod, String deliveryDate) {
        this.testName = testName;
        this.browser = browser;
        this.buttonLocation = buttonLocation;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.color = color;
        this.rentalPeriod = rentalPeriod;
        this.deliveryDate = deliveryDate;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Chrome - верхняя кнопка", "chrome", "top",
                        "Иван", "Иванов", "ул. Лесная, д. 12", "Сокольники", "+79001234567", "black", "сутки", "25.07.2025"},
                {"Chrome - нижняя кнопка", "chrome", "bottom",
                        "Александр", "Александров", "ул. Цветочная, д. 17", "Парк культуры", "+79071234567", "grey", "двое суток", "26.07.2025"},
                {"Firefox - верхняя кнопка", "firefox", "top",
                        "Петр", "Петров", "ул. Лесная, д. 35", "Сокольники", "+79051234567", "grey", "сутки", "27.07.2025"},
                {"Firefox - нижняя кнопка", "firefox", "bottom",
                        "Антон", "Антонов", "ул. Цветочная, д. 5", "Парк культуры", "+79051234567", "black", "двое суток", "28.07.2025"}
        });
    }

    @Before
    public void setUp() {
        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        orderPage = new OrderPage(driver);
        orderPage.open();
        orderPage.closeCookieBannerIfPresent();
    }

    @Test
    public void testOrderFlow() {
        System.out.println("Выполняем тест: " + testName);

        
        if ("top".equals(buttonLocation)) {
            orderPage.clickTopOrderButton();
        } else {
            orderPage.clickBottomOrderButton();
        }

        
        orderPage.setName(name);
        orderPage.setSurname(surname);
        orderPage.setAddress(address);
        orderPage.setMetro(metro);
        orderPage.setPhone(phone);
        orderPage.clickNextButton();

        
        orderPage.setDate(deliveryDate); 
        orderPage.selectRentalPeriod(rentalPeriod); 

        if ("black".equals(color)) {
            orderPage.selectBlackColor();
        } else {
            orderPage.selectGreyColor();
        }
        orderPage.clickBottomOrderButton();
        
        orderPage.confirmOrder();


        
        assertTrue("Не отобразилось сообщение об успешном заказе", orderPage.isSuccessMessageDisplayed());

        System.out.println("Заказ успешно оформлен: " + testName);
    }

    @After
    public void tearDown() {
            driver.quit();
    }
}
