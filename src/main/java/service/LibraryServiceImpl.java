package service;
import database.DatabaseConnection;
import model.Book;
import model.HistoryNote;
import model.Library;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LibraryServiceImpl implements LibraryService, Serializable {
    private final Connection connection;

    public LibraryServiceImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void loadLibraries(String filePath){
        FileLoader<Library> libraryFileLoader = new FileLoader<>(ValuesFromCsvParser::parseLibrary);
        List<Library> libraries = libraryFileLoader.load(filePath);
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO libraries (address) VALUES (?)")) {
           for (Library library : libraries) {
                try {
                    stmt.setString(1, library.getAddress());
                    stmt.addBatch();
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при добавлении библиотеки");
                }
            }
            stmt.executeBatch();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при работе с базой данных" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<HistoryNote> getHistory(int readerId, int libraryId, boolean displayHistory)  {
        List<HistoryNote> history = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement (
                "SELECT * FROM history WHERE reader_id = ? AND library_id = ?")) {
            stmt.setInt(1, readerId);
            stmt.setInt(2, libraryId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistoryNote historyNote = new HistoryNote(rs.getInt("book_id"),rs.getInt("reader_id"), rs.getDate("date").toLocalDate(), rs.getBoolean("is_returned"));
                history.add(historyNote);
            }
            if (displayHistory) {
                System.out.println("History: ");
                history.stream()
                        .filter(historyNote -> historyNote.getReaderId() == readerId)
                        .forEach(historyNote -> System.out.println("BookId: " + historyNote.getBookId() + ", date: " + historyNote.getDate() + (historyNote.isReturned() ? ", Returned" : "")));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных" + e.getMessage());
            e.printStackTrace();
        }
        return history;
    }

    @Override
    public List<Library> getLibraries(boolean displayLibraries) {
        List<Library> libraries = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement (
                "SELECT * FROM libraries")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Library library = new Library(rs.getInt("id"),rs.getString("address"));
                libraries.add(library);
            }
            if (displayLibraries) {
                System.out.println("Libraries: ");
                libraries.forEach(library ->  System.out.println("Id: " + library.getId() + ", address: " + library.getAddress()));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных" + e.getMessage());
            e.printStackTrace();
        }
        return libraries;
    }
}
