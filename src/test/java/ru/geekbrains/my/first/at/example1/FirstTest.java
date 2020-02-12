package ru.geekbrains.my.first.at.example1;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

public class FirstTest {

    @Test
    public void checkNavigation(){
        Selenide.open("https://geekbrains.ru/events");
        System.out.println();
    }
}
