package service;
import exceptions.DatabaseServiceException;
import mapper.HistoryNoteMapper;
import mapper.LibraryMapper;
import model.HistoryNote;
import model.Library;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LibraryServiceImpl implements LibraryService {
    private final Connection connection;

    public LibraryServiceImpl(Connection connection) {
        this.connection = connection;
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
                    throw new DatabaseServiceException("Ошибка при добавлении библиотeки", e);
                }
            }
            stmt.executeBatch();
        }
        catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при загрузке библиотeк", e);
        }
    }

    @Override
    public List<HistoryNote> getHistory(UUID readerId, UUID libraryId, boolean displayHistory)  {
        List<HistoryNote> history = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement (
                "SELECT * FROM history WHERE reader_id = ? AND library_id = ?")) {
            stmt.setObject(1, readerId);
            stmt.setObject(2, libraryId);

            ResultSet rs = stmt.executeQuery();
            HistoryNoteMapper historyNoteMapper = new HistoryNoteMapper();
            while (rs.next()) {
                HistoryNote historyNote = historyNoteMapper.map(rs);
                history.add(historyNote);
            }
            if (displayHistory) {
                System.out.println("History: ");
                history.stream()
                        .filter(historyNote -> historyNote.getReaderId() == readerId)
                        .forEach(historyNote -> System.out.println("BookId: " + historyNote.getBookId() + ", date: " + historyNote.getDate() + (historyNote.isReturned() ? ", Returned" : "")));
            }
        } catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при загрузке истории", e);
        }
        return history;
    }

    @Override
    public List<Library> getLibraries(boolean displayLibraries) {
        List<Library> libraries = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement (
                "SELECT * FROM libraries")) {
            ResultSet rs = stmt.executeQuery();
            LibraryMapper libraryMapper = new LibraryMapper();
            while (rs.next()) {
                Library library = libraryMapper.map(rs);
                libraries.add(library);
            }
            if (displayLibraries) {
                System.out.println("Libraries: ");
                libraries.forEach(library ->  System.out.println("Id: " + library.getId() + ", address: " + library.getAddress()));
            }
        } catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при получении списка библиотек", e);
        }
        return libraries;
    }
}
