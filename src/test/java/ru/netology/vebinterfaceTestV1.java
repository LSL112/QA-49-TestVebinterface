package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class vebinterfaceTestV1 {

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
    void shouldSetPersonalInfoTest() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Сергеев Сергей");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79998887723");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//p[contains(@data-test-id, 'order-success')]//span")).getText();
        String expectedMessage = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage);
     }



}
