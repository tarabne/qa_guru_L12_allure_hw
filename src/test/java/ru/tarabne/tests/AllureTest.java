package ru.tarabne.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tarabne.pages.Steps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class AllureTest extends TestBase {
    private static final String REPOSITORY = "qa-guru/allure-notifications";
    private static final int ISSUE_ID = 180;
    private static final String ISSUE_NAME = "Провести анализ работы с конфигами и чувствительными данными";


    @Test
    @Owner("tarabne")
    @Feature("Issue page")
    @Story("Issues")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка корректности имени Issue на странице Issue с помощью чистого Selenide")
    void checkIssueNameUsingSelenideTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com/");
        $(".header-search-button").click();
        $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $("#issue_" + ISSUE_ID + "_link").click();
        $(".gh-header-show")
                .shouldHave(text(ISSUE_NAME));
    }

    @Test
    @Owner("tarabne")
    @Feature("Issue page")
    @Story("Issues")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка корректности имени Issue на странице Issue с помощью lambda-функций")
    void checkIssueNameUsingLambdaTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открыть главную страницу github.com", () -> {
            open("https://github.com/");
        });
        step("Нажать на поисковую строку", () -> {
            $(".header-search-button").click();
        });
        step("Ввести в поисковую строку запрос \"" + REPOSITORY + "\" и нажимаем enter", () -> {
            $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        });
        step("Перейти в репозиторий \"" + REPOSITORY + "\", кликнув по названию", () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Перейти на вкладку Issues", () -> {
            $("#issues-tab").click();
        });
        step("Перейти в Issue c #" + ISSUE_ID + ", кликнув по названию", () -> {
            $("#issue_" + ISSUE_ID + "_link").click();
        });
        step("Проверить, что Issue с #"+ ISSUE_ID + " отображается заголовок \"" + ISSUE_NAME + "\"", () -> {
            $(".gh-header-show")
                    .shouldHave(text(ISSUE_NAME));
        });
    }

    @Test
    @Owner("tarabne")
    @Feature("Issue page")
    @Story("Issues")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка корректности имени Issue на странице Issue с помощью аннотации @Step")
    void checkIssueNameUsingStepAnnotationTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Steps steps = new Steps();

        steps.openMainPage();
        steps.openAdvancedSearch();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab(ISSUE_NAME);
        steps.openIssuePage(ISSUE_ID);
        steps.issueNameShouldBeCorrect(ISSUE_NAME);
    }
}
