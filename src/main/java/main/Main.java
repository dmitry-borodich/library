package main;

import database.DatabaseConnection;
import database.DatabaseSetup;
import service.BookServiceImpl;
import service.ReaderServiceImpl;
import util.parsers.ArgumentParser;
import service.LibraryServiceImpl;

import java.sql.Connection;

public class Main {
    public static void main(String[] args)  {
        Connection connection = DatabaseConnection.getConnection();
        DatabaseSetup.createTables(connection);
        Adapter adapter = new Adapter(new LibraryServiceImpl(connection), new BookServiceImpl(connection), new ReaderServiceImpl(connection));
        adapter.execute(ArgumentParser.parse(args));
    }
}