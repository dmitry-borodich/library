package service;
import exceptions.LibraryNotFoundException;
import model.HistoryNote;
import model.Library;
import util.FileLoader;
import util.LibraryStatesManager;
import util.parsers.ValuesFromCsvParser;

import java.io.*;
import java.util.*;

public class LibraryServiceImpl implements LibraryService, Serializable {
    private List<Library> libraries = new ArrayList<>();

    @Override
    public void loadLibraries(String filePath){
        FileLoader<Library> libraryFileLoader = new FileLoader<>(ValuesFromCsvParser::parseLibrary);
        libraries = libraryFileLoader.load(filePath);
    }

    @Override
    public Library getLibrary(int id)  {
        for (Library library : libraries) {
            if (library.getId() == id) {
                return library;
            }
        }
        throw new LibraryNotFoundException("Библиотека с номером " + id + " не найдена");
    }

    @Override
    public List<HistoryNote> getHistory(int readerId, int libraryId, boolean displayHistory)  {
        List<HistoryNote> history = getLibrary(libraryId).getHistory();
        if (displayHistory) {
            System.out.println("History: ");
            history.stream()
                    .filter(historyNote -> historyNote.getReaderId() == readerId)
                    .forEach(historyNote -> System.out.println("BookId: " + historyNote.getBookId() + ", date: " + historyNote.getDate() + (historyNote.isReturned() ? ", Returned" : "")));
        }
        return history;
    }
}
