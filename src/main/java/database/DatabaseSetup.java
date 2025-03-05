package database;

import exceptions.DatabaseSetupException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseSetup {
    public static void createTables(Connection connection) {
        try (Statement stmt =  connection.createStatement()) {
            InputStream inputStream = DatabaseSetup.class.getClassLoader().getResourceAsStream("sql/schema.sql");
            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            stmt.execute(sql);
        } catch (Exception e) {
            throw new DatabaseSetupException("Ошибка при создании таблиц", e);
        }
    }

    public static void clearDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                InputStream inputStream = DatabaseSetup.class.getClassLoader().getResourceAsStream("sql/clear_database.sql");
                String sql = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                stmt.execute(sql);
            }
        } catch (Exception e) {
            throw new DatabaseSetupException("Ошибка при очистке базы данных", e);
        }
    }
}
