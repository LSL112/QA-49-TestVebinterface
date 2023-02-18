package ru.netology;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTestV2 {
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
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFailIfNameIsNotRussian() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("T Sergei");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79258582575");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[contains(@class, 'input_invalid')]" +
                "//span[preceding-sibling::span//input[contains(@name, 'name')]][contains(@class, 'input__sub')]")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
