package ru.stellarburgers.pageobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class RegisterPage {

    private final SelenideElement nameInput = $x(".//div[label[text()='Имя']]/input");
    private final SelenideElement emailInput = $x(".//div[label[text()='Email']]/input");
    private final SelenideElement passwordInput = $x(".//input[@name='Пароль']");
    private final SelenideElement registerButton = $x(".//button[text()='Зарегистрироваться']");
    private final SelenideElement wrongPasswordErrorText = $x(".//p[text()='Некорректный пароль']");
    private final SelenideElement loginButton = $x(".//a[@href='/login']");



    public void enterName(String name) {
        nameInput.setValue(name);
    }

    public void enterEmail(String email) {
        emailInput.setValue(email);
    }

    public void enterPassword(String password) {
        passwordInput.setValue(password);
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    @Step("Регистрация пользователя")
    public void registerUser(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }

    @Step("Проверка ошибки короткого пароля")
    public boolean isWrongPasswordErrorVisible() {
        return wrongPasswordErrorText.isDisplayed();
    }

    @Step("Нажать на кнопку 'Войти'")
    public void signInButtonClick() {
        loginButton.click();
    }
}
