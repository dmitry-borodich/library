package main;

import util.parsers.ArgumentParser;
import util.parsers.ParsedArguments;
import service.LibraryServiceImpl;
import util.LibraryStatesManager;

public class Main {
    public static void main(String[] args)  {
        LibraryApp app = new LibraryApp();
        Adapter adapter = app.initialize();
        adapter.execute(ArgumentParser.parse(args));
        app.saveState();
    }
}