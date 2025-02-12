package main;

import exceptions.LibraryNotFoundException;
import parser.ArgumentParser;
import parser.ParsedArguments;
import service.LibraryService;
import service.LibraryStatesManager;

public class Main {
    public static void main(String[] args) throws LibraryNotFoundException {
        LibraryService libraryService = LibraryStatesManager.loadState();
        Adapter adapter = new Adapter(libraryService);
        ParsedArguments parsedArguments = ArgumentParser.parse(args);
        adapter.execute(parsedArguments);
        libraryService.saveState();
    }
}