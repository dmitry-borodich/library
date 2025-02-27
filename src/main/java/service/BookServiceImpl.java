package service;

import database.DatabaseConnection;
import model.Book;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookServiceImpl implements BookService, Serializable {
    private final Connection connection;

    public BookServiceImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void loadBooks(String filePath, int libraryId)  {
        FileLoader<Book> bookFileLoader = new FileLoader<>(ValuesFromCsvParser::parseBook);
        List<Book> books = bookFileLoader.load(filePath);
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO books (title, author, year, is_in_library, library_id) VALUES (?, ?, ?, true, ?)")) {
            books.forEach(book -> {
                try {
                    stmt.setString(1, book.getTitle());
                    stmt.setString(2, book.getAuthor());
                    stmt.setInt(3, book.getYear());
                    stmt.setInt(4, libraryId);

                    stmt.addBatch();
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при добавлении книги");
                }
            });
            stmt.executeBatch();
        }
        catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void lendBook(int readerId, int bookId, int libraryId) {
        try(PreparedStatement checkStmt =  connection.prepareStatement(
                "SELECT is_in_library FROM books WHERE id = ? AND library_id = ?")){
            checkStmt.setInt(1, bookId);
            checkStmt.setInt(2, libraryId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getBoolean("is_in_library")) {
                try (PreparedStatement updateBookStmt = connection.prepareStatement(
                        "UPDATE books SET is_in_library = FALSE WHERE id = ? AND library_id = ?")) {
                    updateBookStmt.setInt(1, bookId);
                    updateBookStmt.setInt(2, libraryId);
                    updateBookStmt.executeUpdate();
                }

                try (PreparedStatement addHistoryNoteStmt = connection.prepareStatement(
                        "INSERT INTO history (book_id, reader_id, date, is_returned, library_id) VALUES (?, ?, ?, FALSE, ?)")) {
                    addHistoryNoteStmt.setInt(1, bookId);
                    addHistoryNoteStmt.setInt(2, readerId);
                    addHistoryNoteStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                    addHistoryNoteStmt.setInt(4, libraryId);
                    addHistoryNoteStmt.executeUpdate();
                }

                System.out.println("Книга " + bookId + " выдана читателю " + readerId);
            }
            else {
                System.out.println("Книга отсутствует в библиотеке");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(int readerId, int bookId, int libraryId) {
        try(PreparedStatement checkStmt = connection.prepareStatement(
                "SELECT * FROM history WHERE reader_id = ? AND library_id = ?")) {
            checkStmt.setInt(1, readerId);
            checkStmt.setInt(2, libraryId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && !rs.getBoolean("is_returned")) {
                try (PreparedStatement updateBookStmt = connection.prepareStatement(
                        "UPDATE books SET is_in_library = TRUE WHERE id = ? AND library_id = ?")) {
                    updateBookStmt.setInt(1, bookId);
                    updateBookStmt.setInt(2, libraryId);
                    updateBookStmt.executeUpdate();
                }
                try (PreparedStatement updateHistoryNoteStmt = connection.prepareStatement(
                        "UPDATE history SET is_returned = TRUE WHERE reader_id = ? AND book_id = ? AND library_id = ?")) {
                    updateHistoryNoteStmt.setInt(1, readerId);
                    updateHistoryNoteStmt.setInt(2, bookId);
                    updateHistoryNoteStmt.setInt(3, libraryId);
                    updateHistoryNoteStmt.executeUpdate();
                }

                System.out.println("Книга " + bookId + " возвращена в библиотеку");
            }
            else {
                System.out.println("Книга находится в билиотеке или информация о взятии не найдена");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getBooks(int libraryId, boolean displayBooks) {
        List<Book> books = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement (
                "SELECT * FROM books WHERE library_id = ?")) {
            stmt.setInt(1, libraryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"),rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getBoolean("is_in_library"));
                books.add(book);
            }
            if (displayBooks) {
                System.out.println("Books: ");
                books.forEach(book ->  System.out.println("Id: " + book.getId() + ", title: " + book.getTitle() + ", author: " + book.getAuthor() + ", year: " + book.getYear() + ", isInLibrary: " + book.getIsInLibrary()));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных" + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }
}
