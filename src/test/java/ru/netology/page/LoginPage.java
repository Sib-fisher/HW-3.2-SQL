package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;

import static com.codeborne.selenide.Selenide.$;


public class LoginPage {
    private final SelenideElement loginField = $("input[name='login']");
    private final SelenideElement passwordField = $("input[name='password']");
    private final SelenideElement loginButton = $$("*").findBy(Condition.text("Продолжить"));
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage validLogin(String login, String password) {
        fillAndSubmit(login, password);
        return new VerificationPage();
    }

    public LoginPage invalidLogin(String login, String password) {
        fillAndSubmit(login, password);
        return this;
    }

    public void verifyError(String expectedText) {
        errorNotification.shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
    }

    // DRY: вынесли повторяющийся код в отдельный метод + добавили очистку
    private void fillAndSubmit(String login, String password) {
        loginField.shouldBe(Condition.visible).clear();
        loginField.setValue(login);
        passwordField.shouldBe(Condition.visible).clear();
        passwordField.setValue(password);
        loginButton.click();
    }
}