import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;

import java.util.Arrays;
import java.util.Collection;



@RunWith(Parameterized.class)
public class AccordionTest {

    private WebDriver driver;
    private MainPage mainPage;

    private final String browser;
    private final int questionIndex;

    public AccordionTest(String browser, int questionIndex) {
        this.browser = browser;
        this.questionIndex = questionIndex;
    }

    @Parameterized.Parameters(name = "{index}: browser={0}, вопрос={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"chrome", 0},
                {"chrome", 1},
                {"chrome", 2},
                {"chrome", 3},
                {"chrome", 4},
                {"chrome", 5},
                {"chrome", 6},
                {"chrome", 7},

                {"firefox", 0},
                {"firefox", 1},
                {"firefox", 2},
                {"firefox", 3},
                {"firefox", 4},
                {"firefox", 5},
                {"firefox", 6},
                {"firefox", 7}
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

        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    public void testEachQuestionDisplaysTextAfterClick() {
        System.out.println("Проверяем вопрос " + (questionIndex + 1) + " в браузере: " + browser);

        mainPage.clickOnQuestion(questionIndex);

        String answerText = mainPage.getAnswerText(questionIndex);

        if (answerText.trim().isEmpty()) {
            System.err.println("Ошибка: Вопрос " + (questionIndex + 1) + " не содержит текста!");
        } else {
            System.out.println("Вопрос " + (questionIndex + 1) + ": текст найден — '" + answerText.trim() + "'");
        }

        Assert.assertFalse("Вопрос " + (questionIndex + 1) + " не содержит текста", answerText.trim().isEmpty());
    }

    @After
    public void tearDown() {
            driver.quit();
    }
}