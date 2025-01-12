package ru.stellarburgers.tests.personalaccounttests;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import ru.stellarburgers.core.BaseTest;
import ru.stellarburgers.dto.User;
import ru.stellarburgers.pageobjects.HomePage;
import ru.stellarburgers.pageobjects.LoginPage;
import ru.stellarburgers.pageobjects.PersonalAccountPage;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stellarburgers.pageobjects.HomePage.TAB_NAME_BUN;

public class PersonalAccountTest extends BaseTest {
    private User user;
    private HomePage homePage;
    private LoginPage loginPage;
    private PersonalAccountPage personalAccountPage;


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
        userApi.createUser(user);

        homePage = open("/", HomePage.class);
        loginPage = homePage.signInButtonClick();
        loginPage.loginUser(user.getEmail(), user.getPassword());
    }

    @Test
    @Description("Проверяем успешный переход в личный кабинет авторизованным пользователем с главной")
    @DisplayName("Успешный переход в личный кабинет авторизованным пользователем по кнопке 'Личный кабинет' с главной")
    public void testAuthorizedUserCanAccessPersonalAccountSuccess() {
        personalAccountPage = homePage.clickPersonalAccountLinkWhenAuthorized();
        token = userApi.loginUserAndGetToken(user);

        assertThat("Пользователь не попал в ЛК", personalAccountPage.isExitButtonEnabled(), is(true));
    }

    @Test
    @Description("Проверяем успешный выход из личного кабинета по кнопке 'Выход'")
    @DisplayName("Успешный выход из личного кабинета по кнопке 'Выход'")
    public void testExitFromPersonalAccountSuccess() {
        personalAccountPage = homePage.clickPersonalAccountLinkWhenAuthorized();
        personalAccountPage.exitButtonClick();
        token = userApi.loginUserAndGetToken(user);

        assertThat("Пользователь не разлогинился", loginPage.isRegisterLinkEnabled(), is(true));
    }

    @Test
    @Description("Проверяем успешный переход на главную из личного кабинета по клику на таб 'Конструктор'")
    @DisplayName("Успешный переход на главную из личного кабинета через таб 'Конструктор' и выбор конструктора 'Булки'")
    public void testRedirectToMainPageFromPersonalAccountViaConstructorTabSuccess() {
        personalAccountPage = homePage.clickPersonalAccountLinkWhenAuthorized();
        personalAccountPage.constructorTabClick();
        token = userApi.loginUserAndGetToken(user);

        assertThat("Таб Булки не выбран" , homePage.isConstructorTabSelected(TAB_NAME_BUN), is(true));
    }

    @Test
    @Description("Проверяем успешный переход на главную из личного кабинета по клику на логотип 'stellar burgers'")
    @DisplayName("Успешный переход на главную из личного кабинета через логотип 'stellar burgers' и выбор конструктора 'Булки'")
    public void testRedirectToMainPageFromPersonalAccountViaLogoSuccess() {
        personalAccountPage = homePage.clickPersonalAccountLinkWhenAuthorized();
        personalAccountPage.stellarBurgersLogoClick();
        token = userApi.loginUserAndGetToken(user);

        assertThat("Таб Булки не выбран" , homePage.isConstructorTabSelected(TAB_NAME_BUN), is(true));
    }
}
