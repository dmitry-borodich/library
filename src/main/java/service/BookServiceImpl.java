package service;

import exceptions.DatabaseServiceException;
import mapper.BookMapper;
import model.Book;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BookServiceImpl implements BookService {
    private final Connection connection;

    public BookServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void loadBooks(String filePath, UUID libraryId)  {
        FileLoader<Book> bookFileLoader = new FileLoader<>(ValuesFromCsvParser::parseBook);
        List<Book> books = bookFileLoader.load(filePath);
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO books (title, author, year, is_in_library, library_id) VALUES (?, ?, ?, true, ?)")) {
            books.forEach(book -> {
                try {
                    stmt.setString(1, book.getTitle());
                    stmt.setString(2, book.getAuthor());
                    stmt.setInt(3, book.getYear());
                    stmt.setObject(4, libraryId);

                    stmt.addBatch();
                } catch (SQLException e) {
                    throw new DatabaseServiceException("Ошибка при добавлении книги", e);
                }
            });
            stmt.executeBatch();
        }
        catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при загрузке книг", e);
        }
    }

    @Override
    public void lendBook(UUID readerId, UUID bookId, UUID libraryId) {
        try(PreparedStatement checkStmt =  connection.prepareStatement(
                "SELECT is_in_library FROM books WHERE id = ? AND library_id = ?")){
            checkStmt.setObject(1, bookId);
            checkStmt.setObject(2, libraryId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getBoolean("is_in_library")) {
                try (PreparedStatement updateBookStmt = connection.prepareStatement(
                        "UPDATE books SET is_in_library = FALSE WHERE id = ? AND library_id = ?")) {
                    updateBookStmt.setObject(1, bookId);
                    updateBookStmt.setObject(2, libraryId);
                    updateBookStmt.executeUpdate();
                }

                try (PreparedStatement addHistoryNoteStmt = connection.prepareStatement(
                        "INSERT INTO history (book_id, reader_id, date, is_returned, library_id) VALUES (?, ?, ?, FALSE, ?)")) {
                    addHistoryNoteStmt.setObject(1, bookId);
                    addHistoryNoteStmt.setObject(2, readerId);
                    addHistoryNoteStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                    addHistoryNoteStmt.setObject(4, libraryId);
                    addHistoryNoteStmt.executeUpdate();
                }

                System.out.println("Книга " + bookId + " выдана читателю " + readerId);
            }
            else {
                System.out.println("Книга отсутствует в библиотеке");
            }

        } catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при выдаче книги", e);
        }
    }

    @Override
    public void returnBook(UUID readerId, UUID bookId, UUID libraryId) {
        try(PreparedStatement checkStmt = connection.prepareStatement(
                "SELECT * FROM history WHERE reader_id = ? AND library_id = ?")) {
            checkStmt.setObject(1, readerId);
            checkStmt.setObject(2, libraryId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && !rs.getBoolean("is_returned")) {
                try (PreparedStatement updateBookStmt = connection.prepareStatement(
                        "UPDATE books SET is_in_library = TRUE WHERE id = ? AND library_id = ?")) {
                    updateBookStmt.setObject(1, bookId);
                    updateBookStmt.setObject(2, libraryId);
                    updateBookStmt.executeUpdate();
                }
                try (PreparedStatement updateHistoryNoteStmt = connection.prepareStatement(
                        "UPDATE history SET is_returned = TRUE WHERE reader_id = ? AND book_id = ? AND library_id = ?")) {
                    updateHistoryNoteStmt.setObject(1, readerId);
                    updateHistoryNoteStmt.setObject(2, bookId);
                    updateHistoryNoteStmt.setObject(3, libraryId);
                    updateHistoryNoteStmt.executeUpdate();
                }

                System.out.println("Книга " + bookId + " возвращена в библиотеку");
            }
            else {
                System.out.println("Книга находится в билиотеке или информация о взятии не найдена");
            }
        } catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при возвращении книги", e);
        }
    }

    @Override
    public List<Book> getBooks(UUID libraryId, boolean displayBooks) {
        List<Book> books = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement (
                "SELECT * FROM books WHERE library_id = ?")) {
            stmt.setObject(1, libraryId);
            ResultSet rs = stmt.executeQuery();
            BookMapper bookMapper = new BookMapper();
            while (rs.next()) {
                Book book = bookMapper.map(rs);
                books.add(book);
            }
            if (displayBooks) {
                System.out.println("Books: ");
                books.forEach(book ->  System.out.println("Id: " + book.getId() + ", title: " + book.getTitle() + ", author: " + book.getAuthor() + ", year: " + book.getYear() + ", isInLibrary: " + book.getIsInLibrary()));
            }
        } catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при получении списка книг", e);
        }
        return books;
    }
}
