package ru.stellarburgers.tests.logintests;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import ru.stellarburgers.core.BaseTest;
import ru.stellarburgers.dto.User;
import ru.stellarburgers.pageobjects.HomePage;
import ru.stellarburgers.pageobjects.LoginPage;
import ru.stellarburgers.pageobjects.PasswordRecoveryPage;
import ru.stellarburgers.pageobjects.RegisterPage;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginUserTest extends BaseTest {
    private User user;
    private HomePage homePage;
    private LoginPage loginPage;


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
        userApi.createUser(user);
    }

    @Test
    @Description("Проверяем успешный вход с главной по кнопке 'Войти в аккаунт'")
    @DisplayName("Успешный вход с главной по кнопке 'Войти в аккаунт'")
    public void testLoginBySignInButtonFromHomePageSuccess() {
        loginPage = homePage.signInButtonClick();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        token = userApi.loginUserAndGetToken(user);

        assertThat("Пользователь не авторизовался", homePage.isPlaceOrderButtonEnabled(), is(true));
    }

    @Test
    @Description("Проверяем успешный вход с главной через кнопку 'Личный кабинет'")
    @DisplayName("Успешный вход с главной через кнопку 'Личный кабинет'")
    public void testLoginByPersonalAccountLinkFromHomePageHeaderSuccess() {
        loginPage = homePage.clickPersonalAccountLinkWhenUnauthorized();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        token = userApi.loginUserAndGetToken(user);

        assertThat("Пользователь не авторизовался", homePage.isPlaceOrderButtonEnabled(), is(true));
    }

    @Test
    @Description("Проверяем успешный вход со страницы регистрации по кнопке 'Войти'")
    @DisplayName("Успешный Вход со страницы регистрации")
    public void testLoginFromRegisterPageSuccess() {
        loginPage = homePage.signInButtonClick();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        registerPage.signInButtonClick();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        token = userApi.loginUserAndGetToken(user);

        assertThat("Пользователь не авторизовался", homePage.isPlaceOrderButtonEnabled(), is(true));
    }

    @Test
    @Description("Проверяем успешный вход со страницы восстановления пароля по кнопке 'Войти'")
    @DisplayName("Успешный Вход со страницы восстановления пароля")
    public void testLoginFromPasswordRecoveryPageSuccess() {
        loginPage = homePage.signInButtonClick();
        PasswordRecoveryPage passwordRecoveryPage = loginPage.clickForgotPasswordLink();
        passwordRecoveryPage.clickLoginButton();
        loginPage.loginUser(user.getEmail(), user.getPassword());
        token = userApi.loginUserAndGetToken(user);

        assertThat("Пользователь не авторизовался", homePage.isPlaceOrderButtonEnabled(), is(true));
    }
}
