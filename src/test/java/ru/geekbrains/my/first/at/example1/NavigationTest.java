//package ru.geekbrains.my.first.at.example1;
//
//import com.codeborne.selenide.Condition;
//import com.codeborne.selenide.Selenide;
//import org.junit.jupiter.api.Test;
//
//public class NavigationTest {
//
//    @Test
//    public void checkNavigation(){
//        Selenide.open("https://geekbrains.ru/courses");
//        Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/courses\"]").click();
//        Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Курсы"));
//
//        Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/events\"]").click();
//        Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Вебинары"));
//
//        Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/topics\"]").click();
//        Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Форум"));
//
//        Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/posts\"]").click();
//        Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Блог"));
//
//        Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/tests\"]").click();
//        Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Тесты"));
//
//        Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/career\"]").click();
//        Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Карьера"));
//    }
//}
