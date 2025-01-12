package ru.stellarburgers.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.stellarburgers.api.ApiClient;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {

    protected ApiClient userApi;
    protected String token;
    protected Faker faker;

    @Before
    public void setUp() {

        String browser = System.getProperty("browser", "chrome");
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://stellarburgers.nomoreparties.site";
        Configuration.timeout = 5000;

        switch (browser.toLowerCase()) {
            case "chrome":
                Configuration.browser = "chrome";
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chrome/chromedriver");
                break;
            case "yandex":
                Configuration.browser = "chrome"; // Используем ChromeDriver для Yandex Browser
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/yandex/yandexdriver");

                ChromeOptions options = new ChromeOptions();
                options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
                options.addArguments("--no-sandbox");
                options.addArguments("--remote-allow-origins=*");

                // Создаем драйвер и передаем его в Selenide
                WebDriver yandexDriver = new ChromeDriver(options);
                WebDriverRunner.setWebDriver(yandexDriver);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
        RestAssured.requestSpecification = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .filter(new AllureRestAssured())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        userApi = new ApiClient(GsonProvider.getGson());
    }

    @After
    public void tearDown() {
        if (token != null) {
            Response response = userApi.deleteUser(token);
            response.then()
                    .statusCode(SC_ACCEPTED)
                    .and()
                    .body("success", equalTo(true));
            token = null;
        }
        Selenide.closeWindow();
    }
}
