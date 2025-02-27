package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void createTables() {
        String createLibrariesTable = """
                CREATE TABLE IF NOT EXISTS libraries (
                id SERIAL PRIMARY KEY,
                address TEXT NOT NULL
                );
            """;
        String createBooksTable = """
                CREATE TABLE IF NOT EXISTS books (
                id SERIAL PRIMARY KEY,
                title VARCHAR(50),
                author VARCHAR(50),
                year INT,
                is_in_library BOOLEAN,
                library_id INTEGER,
                FOREIGN KEY (library_id) REFERENCES libraries (id)
                );
           """;
        String createReadersTable = """
                CREATE TABLE IF NOT EXISTS readers (
                id SERIAL PRIMARY KEY,
                name VARCHAR(50),
                age INT,
                passport_number VARCHAR(50),
                library_id INT,
                FOREIGN KEY (library_id) REFERENCES libraries (id)
                );
            """;
        String createHistoryTable = """
                CREATE TABLE IF NOT EXISTS history (
                book_id INT,
                reader_id INT,
                date DATE,
                is_returned BOOLEAN,
                library_id INT,
                FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
                FOREIGN KEY (reader_id) REFERENCES readers (id) ON DELETE CASCADE,
                FOREIGN KEY (library_id) REFERENCES libraries (id) ON DELETE CASCADE
                );
            """;
        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt =  conn.createStatement()) {
            stmt.execute(createLibrariesTable);
            stmt.execute(createBooksTable);
            stmt.execute(createReadersTable);
            stmt.execute(createHistoryTable);
        } catch (Exception e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void clearDatabase() {
        String clearHistory = "DELETE FROM history";
        String clearBooks = "DELETE FROM books";
        String clearReaders = "DELETE FROM readers";
        String clearLibraries = "DELETE FROM libraries";

        String resetLibrariesSeq = "ALTER SEQUENCE libraries_id_seq RESTART WITH 1";
        String resetBooksSeq = "ALTER SEQUENCE books_id_seq RESTART WITH 1";
        String resetReadersSeq = "ALTER SEQUENCE readers_id_seq RESTART WITH 1";

        try (Connection connection = DatabaseConnection.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(clearHistory);
                stmt.executeUpdate(clearBooks);
                stmt.executeUpdate(clearReaders);
                stmt.executeUpdate(clearLibraries);

                stmt.executeUpdate(resetLibrariesSeq);
                stmt.executeUpdate(resetBooksSeq);
                stmt.executeUpdate(resetReadersSeq);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при очистке базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
