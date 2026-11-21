package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[name='login']");
    private final SelenideElement passwordField = $("[name='password']");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
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
        errorNotification.shouldBe(visible).shouldHave(text(expectedText));
    }

    private void fillAndSubmit(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }
}