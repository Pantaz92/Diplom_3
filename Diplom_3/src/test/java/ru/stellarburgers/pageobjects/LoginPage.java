package ru.stellarburgers.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    private final SelenideElement emailInput = $x("//div[label[text()='Email']]/input");
    private final SelenideElement passwordInput = $x(".//input[@name='Пароль']");
    private final SelenideElement loginButton = $x(".//button[text()='Войти']");
    private final SelenideElement registerLink = $x(".//a[text()='Зарегистрироваться']");
    private final SelenideElement forgotPasswordLink = $x(".//a[text()='Восстановить пароль']");


    public void enterEmail(String email) {
        emailInput.shouldBe(Condition.visible).setValue(email);
    }


    public void enterPassword(String password) {
        passwordInput.setValue(password);
    }


    public void clickLoginButton() {
        loginButton.scrollTo().click();
    }

    @Step("Логин пользователя")
    public void loginUser(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Нажать на ссылку 'Зарегистрироваться'")
    public RegisterPage clickRegisterLink() {
        registerLink.click();
        return page(RegisterPage.class);
    }

    @Step("Нажать на ссылку 'Восстановить пароль'")
    public PasswordRecoveryPage clickForgotPasswordLink() {
        forgotPasswordLink.click();
        return page(PasswordRecoveryPage.class);
    }

    @Step("Проверка видимости ссылки 'Зарегистрироваться'")
    public boolean isRegisterLinkEnabled() {
       return registerLink.isEnabled();
    }
}
