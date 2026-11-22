package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[name='code']");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public DashboardPage validVerify(String code) {
        codeField.shouldBe(Condition.visible).clear();
        codeField.setValue(code);
        verifyButton.click();
        return new DashboardPage();
    }

    public VerificationPage invalidVerify(String wrongCode) {
        codeField.shouldBe(Condition.visible).clear();
        codeField.setValue(wrongCode);
        verifyButton.click();
        return this;
    }

    public void verifyError(String expectedText) {
        errorNotification.shouldBe(Condition.visible).shouldHave(Condition.text(expectedText));
    }
}