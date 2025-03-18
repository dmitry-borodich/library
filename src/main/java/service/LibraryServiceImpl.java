package service;
import lombok.RequiredArgsConstructor;
import model.HistoryNote;
import model.Library;
import org.springframework.stereotype.Service;
import repository.HistoryRepository;
import repository.LibraryRepository;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final HistoryRepository historyRepository;

    @Override
    public void loadLibraries(String filePath){
        FileLoader<Library> libraryFileLoader = new FileLoader<>(ValuesFromCsvParser::parseLibrary);
        List<Library> libraries = libraryFileLoader.load(filePath);
        libraryRepository.saveAll(libraries);
    }

    @Override
    public List<HistoryNote> getHistory(UUID readerId, UUID libraryId, boolean displayHistory)  {
        List<HistoryNote> history = historyRepository.findByReaderIdAndLibraryId(readerId, libraryId);
        if(displayHistory) {
            System.out.println("History: ");
            history.forEach(historyNote -> System.out.println("BookId: " + historyNote.getBookId() + ", date: " + historyNote.getDate() + (historyNote.isReturned() ? ", Returned" : "")));
        }
        return history;
    }

    @Override
    public List<Library> getLibraries(boolean displayLibraries) {
        List<Library> libraries = libraryRepository.findAll();
        if (displayLibraries) {
            System.out.println("Libraries: ");
            libraries.forEach(library -> System.out.println("Id: " + library.getId() + ", address: " + library.getAddress()));
        }
        return libraries;
    }
}
