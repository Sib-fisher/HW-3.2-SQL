package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[name='code']");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public DashboardPage validVerify(String code) {
        codeField.setValue(code);
        verifyButton.click();
        return new DashboardPage();
    }

    public VerificationPage invalidVerify(String wrongCode) {
        codeField.setValue(wrongCode);
        verifyButton.click();
        return this;
    }

    public void verifyError(String expectedText) {
        errorNotification.shouldBe(visible).shouldHave(text(expectedText));
    }
}