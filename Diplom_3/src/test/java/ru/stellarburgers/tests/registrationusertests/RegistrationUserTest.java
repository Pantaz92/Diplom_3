package ru.stellarburgers.tests.registrationusertests;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import ru.stellarburgers.core.BaseTest;
import ru.stellarburgers.dto.User;
import ru.stellarburgers.pageobjects.HomePage;
import ru.stellarburgers.pageobjects.LoginPage;
import ru.stellarburgers.pageobjects.RegisterPage;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegistrationUserTest extends BaseTest {
    private User user;
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;


    @Before
    @Override
    public void setUp() {
        super.setUp();

        faker = new Faker();
        user = new User(
                faker.internet().emailAddress(),
                faker.internet().password(8, 16),
                faker.name().firstName()
        );

        homePage = open("/", HomePage.class);
        loginPage = homePage.signInButtonClick();
        registerPage = loginPage.clickRegisterLink();
    }


    @Test
    @DisplayName("Успешная регистрация с валидными данными")
    @Description("Проверяем успешную регистрацию пользователя с валидными данными")
    public void testRegistrationWithValidCredentialsSuccess() {
        registerPage.registerUser(user.getName(), user.getEmail(), user.getPassword());
        loginPage.loginUser(user.getEmail(), user.getPassword());
        token = userApi.loginUserAndGetToken(user);

        assertThat("Пользователь не авторизован", homePage.isPlaceOrderButtonEnabled(), is(true));
    }

    @Test
    @DisplayName("Неуспешная регистрация с паролем <6 символов")
    @Description("Проверяем ошибку при регистрацим пользователя с паролем <6 символов")
    public void testRegistrationWithPasswordLessThen6CharError() {
        user.setPassword(faker.internet().password(1,5));
        registerPage.registerUser(user.getName(), user.getEmail(), user.getPassword());

        assertThat("Ожидаем ошибку регистрации", registerPage.isWrongPasswordErrorVisible(), is(true));
    }
}
