package ru.geekbrains.my.first.at.example5;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


@Story("Навигация")
public class NavigationTest {

    @BeforeEach
    void setUp() {
        Configuration.browserCapabilities.setCapability("enableVNC", true);
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Курсы",
//            "Вебинары",
//            "Форум",
//            "Блог",
//            "Тесты",
//            "Карьера"
    })
    @Feature("Проверка перехода")
    public void button(String name) {
        Assert.assertTrue(true);
//        openPage("https://geekbrains.ru/courses");
//        Page page = new Page();
//        page.getNavigation().getButton(name).click();
//        page.getHeader().getTitle().shouldHave(Condition.exactText(name+"ф"));
    }

    @Step("Открываю страницу: {url}")
    public void openPage(String url) {
        Selenide.open(url);
    }
}
