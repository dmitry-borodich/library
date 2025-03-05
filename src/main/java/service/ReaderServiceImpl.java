package service;

import exceptions.DatabaseServiceException;
import mapper.ReaderMapper;
import model.Reader;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReaderServiceImpl implements ReaderService{
    private final Connection connection;

    public ReaderServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void loadReaders(String filePath, UUID libraryId)  {
        FileLoader<Reader> readerFileLoader = new FileLoader<>(ValuesFromCsvParser::parseReader);
        List<Reader> readers = readerFileLoader.load(filePath);
        try(PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO readers (name, age, passport_number, library_id) VALUES (?, ?, ?, ?)")) {
            readers.forEach(reader -> {
                try {
                    stmt.setString(1, reader.getName());
                    stmt.setInt(2, reader.getAge());
                    stmt.setString(3, reader.getPassportNumber());
                    stmt.setObject(4, libraryId);

                    stmt.addBatch();
                } catch (SQLException e) {
                    throw new DatabaseServiceException("Ошибка при добавлении читателя", e);
                }
            });

            stmt.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при загрузке читателей", e);
        }
    }

    @Override
    public List<Reader> getReaders(UUID libraryId)  {
        List<Reader> readers = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM readers WHERE library_id = ?")) {
            stmt.setObject(1, libraryId);
            ResultSet rs = stmt.executeQuery();
            ReaderMapper readerMapper = new ReaderMapper();
            while(rs.next()) {
                Reader reader = readerMapper.map(rs);
                readers.add(reader);
            }
            System.out.println("Readers: ");
            readers.forEach(reader -> System.out.println("Id: " + reader.getId() + ", name: " + reader.getName() +", age: " + reader.getAge() + ", passport number: " + reader.getPassportNumber()));
        } catch (SQLException e) {
            throw new DatabaseServiceException("Ошибка при получении списка читателей", e);
        }
        return readers;
    }
}
