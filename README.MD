# Разворачиваем инфраструктуру для автоматизации тестирования WEB-приложений на macOS

## Настройка
### Установка JDK

Скачать openjdk:

```
https://adoptopenjdk.net/index.html?variant=openjdk8&jvmVariant=hotspot
```
Установить по умолчанию

### Установка MAVEN

Скачать:
```
http://mirror.linux-ia64.org/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip
```
Переместить :
```
sudo mkdir /Library/maven
sudo mv /Users/antonpavlov/Downloads/apache-maven-3.6.3 /Library/maven
```
### Git

Скачать
```
https://git-scm.com
```
Установка по умолчанию

### Прописать jdk и maven в переменное окружение
```
nano ~/.bash_profile

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home
export PATH=$PATH:$JAVA_HOME/bin
export M2_HOME=/Library/maven/apache-maven-3.6.3
export PATH=$PATH:$M2_HOME/bin
```

### Проверить что jdk, maven, git успешно установлены
```
java -version
mvn -v
git --version
```


### Docker
Зарегистрироваться:
```
https://hub.docker.com/signup?next=%2F%3Fref%3Dlogin
```

Скачать:
```
https://hub.docker.com/editions/community/docker-ce-desktop-mac
```

### Selenoid
Полезные ссылки:
```
https://aerokube.com/selenoid/latest/
https://aerokube.com/cm/latest/
```
Устанавливаем CM:
```
curl -s https://aerokube.com/cm/bash | bash
```
Быстрый старт:
```
./cm selenoid start --vnc --browsers 'chrome:80'
./cm selenoid-ui start
```
Старт Selenoid-UI отдельным контейнером с явным указанием ссылки на Selenoid
```
docker run -d --name selenoid-ui  --link selenoid -p 8080:8080 aerokube/selenoid-ui --selenoid-uri=http://192.168.31.48:4444
```
После исправления от Aerokube должно заработать командой:
```
./cm selenoid-ui start -force --args "-selenoid-uri http://192.168.31.48:4444"
```
Selenoid доступен:
```
http://localhost:8080
```

### Jenkins
Полезные ссылки:
```
https://jenkins.io/
https://hub.docker.com/r/jenkins/jenkins/
```
Команда для установки:
```
docker run --name jenkins -p 8000:8080 -p 50000:50000 -v /Users/antonpavlov/Documents/jenkins_home:/var/jenkins_home jenkins/jenkins:lts
```

Jenkins доступен:
```
http://localhost:8000
```

### Установка и настройка IDEA
Скачать
```
https://www.jetbrains.com/ru-ru/idea/download/
```

##### Настройка при установке
1 окно
2 окно
- отключить все не нужные плагины

##### Настройка после установки

Перейти в Preferences
```
вкладка enable 
	включить чекбокс: annotation processing
вкладка Maven
	указать путь /Library/maven/apache-maven-3.6.3
	вкладка Importing
		указать 1.8 jdk
вкладка git 
	проверить работу нажав: test
вкладка file encoding
	включить чекбокс: transparent native-to-ascii convert
```
Перейти в plugins и установить:
```
lombok
```
Перейти в Structure for New Projects
```
sdk -  выбрать 1.8
```

### Старт проекта
```
В Idea нажать New Project
Выбрать Maven - без архетипа
Название проекта:
my-first-at
Finish
```

### Работа с pom:
#### Подключение плагинов:
```
<!--Для работы с кириллицей-->
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<!--https://www.apache-maven.ru/plugins/maven-compiler-plugin.html-->
<!--Явно указывает компилятору что используем java 8-->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>

<!--Запуск локального веб сервера для просмотра отчета allure-->
<!--https://github.com/allure-framework/allure-maven-->
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.10.0</version>
    <configuration>
        <reportVersion>2.13.0</reportVersion>
        <resultsDirectory>allure-results</resultsDirectory>
    </configuration>
</plugin>

<!--Запуск тестов -->
<!--https://www.apache-maven.ru/plugins/maven-surefire-plugin.html-->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.1</version>
    <configuration>
        <!--Количество потоков-->
        <threadCount>1</threadCount>
        <!--Как будет распаралеливать-->
        <parallel>methods</parallel>
        <!--true - количество потоков будет на каждое ядро, false - распределится-->
        <perCoreThreadCount>false</perCoreThreadCount>
        <!--true - Статус билда не зависит от прохождения тестов-->
        <testFailureIgnore>true</testFailureIgnore>
        <!--Количество попыток для прохождения теста-->
        <rerunFailingTestsCount>2</rerunFailingTestsCount>
        <!--Аргументы которые будут добавлены при запуске тестов-->
        <argLine>
            -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/1.9.1/aspectjweaver-1.9.1.jar"
        </argLine>
    </configuration>
    <!-- агент, который слушаетсобытия Allure и собирает информацию для отчета-->
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.1</version>
        </dependency>
    </dependencies>
</plugin>
```

#### Подключение библиотек
```
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <!--WEB ТЕСТИРОВАНИЕ-->
    <selenide.version>5.7.0</selenide.version>
    <!--ОТЧЕТ ALLURE-->
    <allure.version>2.13.0</allure.version>
    <!--LOMBOK-->
    <lombok.version>1.18.10</lombok.version>
</properties>

<dependencies>
    <!--WEB ТЕСТИРОВАНИЕ-->
    <dependency>
        <groupId>com.codeborne</groupId>
        <artifactId>selenide</artifactId>
        <version>${selenide.version}</version>
    </dependency>
    <!--ОТЧЕТ ALLURE-->
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-selenide</artifactId>
        <version>${allure.version}</version>
    </dependency>
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-junit5</artifactId>
        <version>${allure.version}</version>
    </dependency>
    <!--LOMBOK-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>${lombok.version}</version>
    </dependency>
</dependencies>
```

## Написание автотеста
##### Тестируемый сайт: https://geekbrains.ru/events

#### Создание корневого пакета
Пакеты необходимы для размещения файлов по папкам, решения проблемы коллизии имен и для разделения доступа классов друг к другу. Именно благодаря пакетам мы можем через точку . получать доступ к нужным классам различных библиотек.
```
ru.geekbrains.my.first.at
```

#### Создание первого класса в test
```
FirstTest
```
#### Создание пакетов для хранения разных подходов
Создать пакеты 
```
example1 example2 example3
```
#### Создание первого класса в test
Шаги теста:
```
1.Зайти на сайт https://geekbrains.ru/courses
2.Нажать на кнопку Курсы
3.Проверить что страница Курсы открылась
4.Повторить для
     1.Курсы
     2.Вебинары
     3.Форум
     4.Блог
     5.Тесты
     6.Карьера
```

#### Как искать элемент на странице

XPath
```
Основой HTML-документа являются теги.
В соответствии с объектной моделью документа («Document Object Model», коротко DOM), каждый HTML-тег является объектом. Вложенные теги являются «детьми» родительского элемента. Текст, который находится внутри тега, также является объектом.

Xpath - язык запросов к элементам по DOM. 
```
CSS
```
CSS (Cascading Style Sheets) — язык таблиц стилей, который позволяет прикреплять стиль (например, шрифты и цвет) к структурированным документам (например, документам HTML и приложениям XML).

В CSS есть паттерны, согласно которым стили, создаваемые разработчиком, применяются к элементам страницы (DOM). Эти паттерны называются локаторы (selectors). Selenium WebDriver использует тот же принцип для нахождения элементов. И он намного быстрее, чем поиск элементов на основе XPath.
```

#### Первый тест "Простой пример"

```java
Selenide.open("https://geekbrains.ru/courses");
Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/courses\"]").click();
Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Курсы"));

Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/events\"]").click();
Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Вебинары"));

Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/topics\"]").click();
Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Форум"));

Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/posts\"]").click();
Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Блог"));

Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/tests\"]").click();
Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Тесты"));

Selenide.$("[class=\"gb-left-menu__nav\"] [href=\"/career\"]").click();
Selenide.$("[class=\"gb-header__title\"]").shouldHave(Condition.exactText("Карьера"));
```

#### Распаралеливаем тесты
```
http://antkorwin.com/junit5/junit5_parallel_execution.html
```
