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
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Sergeev Sergei");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79258582575");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[@data-test-id='name'] [contains(@class, 'input_invalid')]//span[@class='input__sub']")).getText().trim();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfWrongNumber() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Сергеев Сергей");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+7925858257");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[@data-test-id='phone'] [contains(@class, 'input_invalid')]//span[@class='input__sub']")).getText().trim();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfNameInputIsEmpty() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79258582575");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[@data-test-id='name'] [contains(@class, 'input_invalid')]//span[@class='input__sub']")).getText().trim();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfPhoneInputIsEmpty() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Сергеев Сергей");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("");
        driver.findElement(By.xpath(".//span[contains(@class, 'checkbox__box')]")).click();
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//span[@data-test-id='phone'] [contains(@class, 'input_invalid')]//span[@class='input__sub']")).getText().trim();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldFailIfCheckboxIsEmpty() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Сергеев Сергей");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79258582575");
        driver.findElement(By.xpath(".//span[contains(text(), 'Продолжить')]")).click();
        String actualMessage = driver.findElement(By.xpath("//label[contains(@data-test-id, 'agreement')]//span[contains(@class, 'checkbox__text')]")).getText().trim();
        System.out.println(actualMessage);
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
