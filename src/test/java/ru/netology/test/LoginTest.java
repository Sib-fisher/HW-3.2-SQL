package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeEach
    void setUp() {
        SQLHelper.cleanDatabase();
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginSuccessfully() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(DataHelper.getLogin(), DataHelper.getPassword());
        String codeFromDb = SQLHelper.getLastAuthCode(DataHelper.getLogin());
        verificationPage.validVerify(codeFromDb);
    }

    @Test
    void shouldBlockAfterThreeWrongPasswords() {
        var loginPage = new LoginPage();

        for (int i = 0; i < 3; i++) {
            loginPage = loginPage.invalidLogin(DataHelper.getLogin(), "wrongPass");
            loginPage.verifyError("Неверно указан логин или пароль");
        }

        loginPage = loginPage.invalidLogin(DataHelper.getLogin(), DataHelper.getPassword());
        loginPage.verifyError("Пользователь заблокирован");
    }
}