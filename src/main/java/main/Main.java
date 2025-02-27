package main;

import database.DatabaseSetup;
import service.BookServiceImpl;
import service.ReaderServiceImpl;
import util.parsers.ArgumentParser;
import service.LibraryServiceImpl;

public class Main {
    public static void main(String[] args)  {
        DatabaseSetup.createTables();
        Adapter adapter = new Adapter(new LibraryServiceImpl(), new BookServiceImpl(), new ReaderServiceImpl());
        adapter.execute(ArgumentParser.parse(args));
    }
}