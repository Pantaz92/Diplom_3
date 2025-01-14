package ru.stellarburgers.pageobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PersonalAccountPage {

    private final SelenideElement exitButton = $x(".//button[text()='Выход']");
    private final SelenideElement constructorTab = $x(".//a[@href='/'][p[text()='Конструктор']]");
    private final SelenideElement stellarBurgersLogo = $x(".//div[contains(@class, 'logo')]/a[@href='/']");

    @Step("Нажать на кнопку 'Выход'")
    public void exitButtonClick() {
        exitButton.click();
    }

    @Step("Нажать на таб в хедере 'Конструктор'")
    public void constructorTabClick() {
        constructorTab.click();
    }

    @Step("Нажать на логотип в хедере 'stellar burgers'")
    public void stellarBurgersLogoClick() {
        stellarBurgersLogo.click();
    }

    @Step("Проверка видимости кнопки 'Выход'")
    public boolean isExitButtonEnabled() {
        return exitButton.isEnabled();
    }
}
