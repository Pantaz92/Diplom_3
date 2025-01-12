package ru.stellarburgers.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class HomePage {

    private final SelenideElement loginButton = $x(".//button[text()='Войти в аккаунт']");
    private final SelenideElement placeOrderButton = $x(".//button[text()='Оформить заказ']");
    private final String constructorTabLocator = ".//div[span[text()='%s']]";
    private final SelenideElement personalAccountLinkButton = $x(".//a[p[text()='Личный Кабинет']]");
    public final static String TAB_NAME_BUN = "Булки";


    @Step("Нажать на кнопку 'Войти в аккаунт'")
    public LoginPage signInButtonClick() {
        loginButton.shouldBe(Condition.visible).click();
        return page(LoginPage.class);
    }

    @Step("Проверка видимости кнопки 'оформить заказ'")
    public boolean isPlaceOrderButtonEnabled() {
        placeOrderButton.shouldBe(Condition.visible);
        return placeOrderButton.isEnabled();
    }


    @Step("Нажать на вкладку конструктора {nameOfTab}")
    public void clickConstructorTab(String nameOfTab) {
        SelenideElement constructorTab = $x(String.format(constructorTabLocator, nameOfTab));
        if (!constructorTab.getAttribute("class").contains("current")) {
            constructorTab.click();
        }
    }

    @Step("Проверка, выбрана ли вкладка конструктора {nameOfTab}")
    public boolean isConstructorTabSelected(String nameOfTab) {
        SelenideElement constructorTab = $x(String.format(constructorTabLocator, nameOfTab));
        constructorTab.shouldBe(Condition.visible);
        return constructorTab.getAttribute("class").contains("current");
    }

    @Step("Нажать на кнопку 'Личный кабинет' будучи неавторизованным пользователем")
    public LoginPage clickPersonalAccountLinkWhenUnauthorized() {
        personalAccountLinkButton.click();
        return page(LoginPage.class);
    }

    @Step("Нажать на кнопку 'Личный кабинет' будучи авторизованным пользователем")
    public PersonalAccountPage clickPersonalAccountLinkWhenAuthorized() {
        personalAccountLinkButton.click();
        return page(PersonalAccountPage.class);
    }
}
