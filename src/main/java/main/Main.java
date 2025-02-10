package main;

import Parser.ArgumentParser;
import Parser.ParsedArguments;
import service.LibraryService;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = LibraryService.loadState();
        Adapter adapter = new Adapter(libraryService);
        ParsedArguments parsedArguments = ArgumentParser.parse(args);
        adapter.execute(parsedArguments);
        libraryService.saveState();
    }
}