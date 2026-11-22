package ru.netology.data;

public class DataHelper {
    private DataHelper() {}

    public static String getLogin() {
        return "vasya";
    }

    public static String getPassword() {
        return "qwerty123";
    }

    // Генерация случайного пароля для теста блокировки
    public static String getRandomPassword() {
        return "wrongPass" + System.currentTimeMillis();
    }
}