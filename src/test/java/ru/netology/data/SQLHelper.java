package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.mode.User;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {}

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app",
                "app",
                "pass"
        );
    }

    public static String getLastAuthCode(String login) {
        var codeSQL = "SELECT code FROM auth_codes ac " +
                "JOIN users u ON ac.user_id = u.id " +
                "WHERE u.login = ? ORDER BY ac.created DESC LIMIT 1";
        try (var conn = getConnection()) {
            return runner.query(conn, codeSQL, new ScalarHandler<>(), login);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении кода из БД", e);
        }
    }

    public static User getUserByLogin(String login) {
        var userSQL = "SELECT * FROM users WHERE login = ?";
        try (var conn = getConnection()) {
            return runner.query(conn, userSQL, new BeanHandler<>(User.class), login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanDatabase() {
        try (var conn = getConnection()) {
            runner.update(conn, "SET FOREIGN_KEY_CHECKS = 0");
            runner.update(conn, "TRUNCATE TABLE auth_codes");
            runner.update(conn, "TRUNCATE TABLE cards");
            runner.update(conn, "UPDATE users SET status = 'active'");
            runner.update(conn, "SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки БД", e);
        }
    }
}