package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderFormTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldSubmitValidForm() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");

        driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__box")).click();

        driver.findElement(By.cssSelector("button")).click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test-id='order-success']")
        ));


        String expectedText = "Ваша заявка успешно отправлена!";
        String actualText = successMessage.getText();
        assertTrue(actualText.contains(expectedText), "Текст не совпадает. Ожидалось: " + expectedText);
    }
}