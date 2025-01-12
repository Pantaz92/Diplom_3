package ru.stellarburgers.tests.constructortests;

import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.stellarburgers.core.BaseTest;
import ru.stellarburgers.pageobjects.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class ConstructorTest extends BaseTest {
    private final String tabName;
    private final boolean isTabSelected;

    public ConstructorTest(String tabName, boolean isTabSelected) {
        this.tabName = tabName;
        this.isTabSelected = isTabSelected;
    }

    @Parameterized.Parameters(name = "Таб: {0} выбран, ожидаемый результат {1}")
    public static Object[][] getData() {
        return new Object[][] {
                {"Булки", true},
                {"Соусы", true},
                {"Начинки", true}
        };
    }

    @Test
    @Description("Проверяем выбираются ли табы в конструктор")
    @DisplayName("Успешный выбор табов в конструкторе")
    public void testTabSelectionIsSuccessful() {
        HomePage homePage = open("/", HomePage.class);
        homePage.clickConstructorTab(tabName);

        assertThat(String.format("Таб %s не выбран", tabName), homePage.isConstructorTabSelected(tabName), is(isTabSelected));
    }
}
